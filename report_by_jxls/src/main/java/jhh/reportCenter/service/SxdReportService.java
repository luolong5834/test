package jhh.reportCenter.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jhh.reportCenter.Consts;
import jhh.reportCenter.model.BizReportSourceSxd;
import jhh.reportCenter.model.SxdReport;
import jhh.reportCenter.model.TdMapping;
import jhh.reportCenter.model.VCisMapping;
import jhh.reportCenter.model.dto.SxdReportDto;
import jhh.reportCenter.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/1/22
 */
@Service
@Slf4j
public class SxdReportService {
    //根据单个cos大小和es存储doc数据量决定
    private final int QUERY_MAX_SIZE = 1000;

    @Autowired
    protected Client client;

    /**
     * es获取报文
     * 1，获取上海资信，同盾的报文
     *
     * @param [productId, sources]
     * @return java.util.List<jhh.reportCenter.model.dto.SxdReportDto>
     * @author long.luo
     * @date 2019/1/25
     */
    public List<SxdReportDto> findSpecialReportList(String productId, List<BizReportSourceSxd> sources) throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        if (StringUtils.isEmpty(productId)) {
            throw new IllegalArgumentException("productId为空！");
        }
        if (sources == null || sources.isEmpty()) {
            return Collections.emptyList();
        }
        Set<String> cardNoSet = sources.stream().map(o -> o.getCardNo()).collect(Collectors.toSet());
        if (cardNoSet == null || cardNoSet.size() > QUERY_MAX_SIZE) {
            throw new IllegalArgumentException("嘿嘿，cardNos有问题");
        }
        SearchRequestBuilder builderCis = prepareSearch();
        SearchRequestBuilder builderTd = prepareSearch();
        BoolQueryBuilder boolQueryBuilderCis = QueryBuilders.boolQuery();
        BoolQueryBuilder boolQueryBuilderTongdun = QueryBuilders.boolQuery();
        //生成条件
        TermQueryBuilder condition1 = QueryBuilders.termQuery("product_id", productId);
        TermsQueryBuilder condition2 = QueryBuilders.termsQuery("card_no", cardNoSet.toArray(new String[cardNoSet.size() - 1]));
        TermsQueryBuilder conditionCis = QueryBuilders.termsQuery("risk_org_code", "cis");
        TermsQueryBuilder conditionTongdun = QueryBuilders.termsQuery("risk_org_code", "tongdun");

        String[] includeFieldsCis = {"card_no", "personal_name", "product_id", "risk_org_code", "个人身份信息.地址.地址明细", "个人身份信息.工作单位.工作明显", "贷款交易信息.信息概要.贷款笔数",
                "贷款交易信息.贷款.逾期31-60天未归还贷款本金", "贷款交易信息.贷款.逾期61-90天未归还贷款本金",
                "贷款交易信息.贷款.逾期91-180天未归还贷款本金", "贷款交易信息.贷款.逾期180天以上未归还贷款本金"};
        //绑定条件
        boolQueryBuilderCis.must(condition1).must(condition2).must(conditionCis);
        boolQueryBuilderTongdun.must(condition1).must(condition2).must(conditionTongdun);
        builderCis.setQuery(boolQueryBuilderCis);
        builderCis.setFetchSource(includeFieldsCis, null);
        builderTd.setQuery(boolQueryBuilderTongdun);
        builderCis.setFrom(0).setSize(QUERY_MAX_SIZE);
        MultiSearchResponse multiSearchResponse = client.prepareMultiSearch()
                .add(builderCis)
                .add(builderTd)
                .get();
        Map<String, VCisMapping> mapCis = new HashMap();
        Map<String, TdMapping> mapTd = new HashMap();
        int forFlag = 0;
        for (MultiSearchResponse.Item item : multiSearchResponse) {
            forFlag++;
            if (forFlag == 1) {
                if (item.isFailure()) {
                    log.error("从es获取随心贷风控数据错误!,错误描述:{}", item.getFailureMessage());
                    return null;
                }
                SearchResponse reponseCis = item.getResponse();
                if (reponseCis.status() != RestStatus.OK) {
                    return null;
                }
                SearchHits hits = reponseCis.getHits();
                for (SearchHit hit : hits.getHits()) {
                    String source = hit.getSourceAsString();
                    if (StringUtils.isEmpty(source))
                        continue;
                    VCisMapping mapping = JSONObject.parseObject(source, VCisMapping.class);
                    mapCis.put(mapping.getCardNo(), mapping);
                }
            }
            if (forFlag == 2) {
                if (item.isFailure()) {
                    log.error("从es获取随心贷风控数据错误!,错误描述:{}", item.getFailureMessage());
                    return null;
                }
                SearchResponse reponseTd = item.getResponse();
                if (reponseTd.status() != RestStatus.OK) {
                    return null;
                }
                SearchHits hits = reponseTd.getHits();
                for (SearchHit hit : hits.getHits()) {
                    String source = hit.getSourceAsString();
                    if (StringUtils.isEmpty(source))
                        continue;
                    TdMapping mapping = JSONObject.parseObject(source, TdMapping.class);
                    mapTd.put(mapping.getCardNo(), mapping);
                }
            }
        }
        stopWatch.stop();
        log.info("从ES获取{}条数据耗时:{}", sources.size(), stopWatch.getTotalTimeSeconds());
        stopWatch.start("2");
        List<SxdReport> resultList = new LinkedList<>();
        //将上海资信，同盾数据合并
        for (Map.Entry<String, VCisMapping> entry : mapCis.entrySet()) {
            SxdReport sxdReport = new SxdReport();
            sxdReport.setVCisMapping(entry.getValue());
            sxdReport.setTdMapping(mapTd.get(entry.getKey()));
            resultList.add(sxdReport);
        }
        //解析数据
        List<SxdReportDto> newResult = analysis(resultList);
        //组合原和目标
        newResult = combinationData(sources, newResult);
        stopWatch.stop();
        log.info("解析{}数据耗时：{}", sources.size(), stopWatch.getTaskInfo()[1].getTimeSeconds());
        return newResult;
    }

    /**
     * 组装源对象和结果对象
     * 1，根据源对象没查到结果对象
     * 2，源对象 查到了结果对象，吧结果对象查到的值赋给源对象
     *
     * @param [sources, resultList]
     * @return void
     * @author long.luo
     * @date 2019/1/25
     */
    private List<SxdReportDto> combinationData(List<BizReportSourceSxd> sources, List<SxdReportDto> resultList) throws Exception {

        Map<String, SxdReportDto> map = new HashMap();
        for (SxdReportDto dto : resultList) {
            map.put(dto.getCardNo(), dto);
        }
        List<SxdReportDto> newList = new ArrayList<>();
        for (BizReportSourceSxd bizReportSourceSxd : sources) {
            SxdReportDto dto = new SxdReportDto();
            SxdReportDto sourceDto = map.get(bizReportSourceSxd.getCardNo());
            PropertyUtils.copyProperties(dto, bizReportSourceSxd);
            if (sourceDto != null) {
                PropertyUtils.copyProperties(dto, sourceDto);
                PropertyUtils.copyProperties(dto, bizReportSourceSxd);
            }
            newList.add(dto);
        }
        return newList;
    }

    /**
     * 解析es获取的对象
     * 1，吧解析得到的数据分析成自己想要的报告对象
     * 2，具体请参照[report_sxd_template.xlsx]报告模板
     *
     * @param [data]
     * @return java.util.List<jhh.reportCenter.model.dto.SxdReportDto>
     * @author long.luo
     * @date 2019/1/25
     */
    private List<SxdReportDto> analysis(List<SxdReport> data) {
        List<SxdReportDto> newData = new ArrayList<>();
        for (SxdReport dto : data) {
            VCisMapping mappingCis = dto.getVCisMapping();
            TdMapping mappingTd = dto.getTdMapping();
            SxdReportDto sxdReportDto = new SxdReportDto();
            //设置身份证
            if (mappingCis != null) {
                sxdReportDto.setCardNo(mappingCis.getCardNo());
                sxdReportDto.setPhone(mappingCis.getPhone());
            } else if (mappingTd != null) {
                sxdReportDto.setCardNo(mappingTd.getCardNo());
                sxdReportDto.setPhone(mappingTd.getPhone());
            }
            Object personInfoObject = mappingCis.getPersonalIdentityInformation();
            if (personInfoObject != null) {
                JSONObject personInfo = (JSONObject) personInfoObject;
                log.debug("personInfo:" + personInfo.toJSONString());
                //地址栏
                JSONArray addrArray = JSONUtil.getJSONArray(personInfo, "地址");
                //工作栏
                JSONArray wordAddrArray = JSONUtil.getJSONArray(personInfo, "工作单位");
                if (addrArray != null) {
                    Set<String> set = new TreeSet<>((new Comparator<String>() {
                        @Override
                        public int compare(String s1, String s2) {
                            return s1.compareTo(s2);
                        }
                    }));
                    for (int i = 0; i < addrArray.size(); i++) {
                        JSONObject jsonObject1 = addrArray.getJSONObject(i);
                        String homeAddr = jsonObject1.getString("地址明细");
                        set.add(homeAddr);
                    }
                    if (addrArray.size() == 0) {
                        sxdReportDto.setCisAddrStatus("未知");
                    } else if (set.size() == 1) {
                        sxdReportDto.setCisAddrStatus("是");
                    } else {
                        sxdReportDto.setCisAddrStatus("否");
                    }
                }

                if (wordAddrArray != null) {
                    Set<String> set = new TreeSet<>((new Comparator<String>() {
                        @Override
                        public int compare(String s1, String s2) {
                            return s1.compareTo(s2);
                        }
                    }));
                    for (int i = 0; i < addrArray.size(); i++) {
                        JSONObject jsonObject1 = addrArray.getJSONObject(i);
                        String homeAddr = jsonObject1.getString("工作明细");
                        set.add(homeAddr);
                    }
                    if (addrArray.size() == 0) {
                        sxdReportDto.setCisWorkAddrStatus("未知");
                    } else if (set.size() == 1) {
                        sxdReportDto.setCisWorkAddrStatus("是");
                    } else {
                        sxdReportDto.setCisWorkAddrStatus("否");
                    }
                }
            }
            //贷款信息
            JSONObject daikuanInfo = (JSONObject) mappingCis.getLoanTransactionInformation();
            if (daikuanInfo != null) {
                JSONObject xinxigaiyao = daikuanInfo.getJSONObject("信息概要");
                String duaikuanCount = "0";//贷款笔数
                if (xinxigaiyao != null) {
                    duaikuanCount = xinxigaiyao.getString("贷款笔数");
                }
                sxdReportDto.setCisDaikuanCount(duaikuanCount);
                //逾期31-60天未归还贷款本金
                JSONArray daikuan = JSONUtil.getJSONArray(daikuanInfo, "贷款");
                Integer yuqi31_60 = 0, yuqi60_91 = 0, yuqi91_180 = 0, yuqi180 = 0;
                if (daikuan != null) {
                    for (int i = 0; i < daikuan.size(); i++) {
                        JSONObject o = daikuan.getJSONObject(i);
                        yuqi31_60 += o.getInteger("逾期31-60天未归还贷款本金");
                        yuqi60_91 += o.getInteger("逾期61-90天未归还贷款本金");
                        yuqi91_180 += o.getInteger("逾期91-180天未归还贷款本金");
                        yuqi180 += o.getInteger("逾期180天以上未归还贷款本金");
                    }
                }
                sxdReportDto.setCisYuqi31_60(yuqi31_60.toString());
                sxdReportDto.setCisYuqi61_90(yuqi60_91.toString());
                sxdReportDto.setCisYuqi91_180(yuqi91_180.toString());
                sxdReportDto.setCisYuqi180_(yuqi180.toString());
            }

            //td 身份证命中高风险关注名单 ,手机号命中虚假号码库
            if (mappingTd != null) {
                Object riskItemObject = mappingTd.getRiskItems();
                if (riskItemObject != null) {
                    log.debug("riskItemObject:{}", riskItemObject.toString());
                    JSONArray riskItems = (JSONArray) mappingTd.getRiskItems();
                    for (int i = 0; i < riskItems.size(); i++) {
                        JSONObject riskItemJson = riskItems.getJSONObject(i);
                        if (!StringUtils.isEmpty(riskItemJson.getString("身份证命中高风险关注名单"))) {
                            String riskLevel = riskItemJson.getString("risk_level");
                            sxdReportDto.setTdCardLevel(riskLevel);
                        }
                        if (!StringUtils.isEmpty(riskItemJson.getString("手机号命中虚假号码库"))) {
                            String riskLevel = riskItemJson.getString("risk_level");
                            sxdReportDto.setTdPhoneLevel(riskLevel);
                        }
                    }
                }
            }

            newData.add(sxdReportDto);
        }
        return newData;
    }

    private SearchRequestBuilder prepareSearch() {
        return client.prepareSearch(Consts.SEARCH_INDEX_MAPPING);
    }

    /*public static void main(String[] args) {
        String jsonArray = "{\"地址\":{\"地址明细\":\"河北省邯郸市磁县磁县朝阳路27号\"}}";
        String jsonArray2 = "{\"地址\":[{\"地址明细\":\"辽宁省抚顺市顺城区临江东路19-6号楼一单元2802\"},{\"地址明细\":\"辽宁省|抚顺市|顺城\"},{\"地址明细\":\"辽宁省抚顺市顺城区临江东路19-2-2802号\"}]}";
        JSONArray aa = JSONObject.parseObject(jsonArray2).getJSONArray("地址明细");
        System.out.println( );

    }*/
}
