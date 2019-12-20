package jhh.reportCenter.test;

import org.elasticsearch.index.query.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/1/23
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SxdReportTest {
    private final String riskOrgCode = "cis";

    @Test
    public void sxdReportTest() {
       /* String types[] = {"jhh-risk-vcis","jhh-risk-td"};
        Searcher<?> cisSearch = SearcherFactory.createOnCode(Consts.VCIS_NAME);

        TermsQueryBuilder queryBuild = QueryBuilders.termsQuery("card_no", "610322199101033610", "370404199006301915");
        TermQueryBuilder queryBuild2 = QueryBuilders.termQuery("product_id", "76");
        String[] includeFields = {"card_no","personal_name","product_id","risk_org_code","个人身份信息.地址.地址明细","个人身份信息.工作单位.工作明显","贷款交易信息.信息概要.贷款笔数",
                "贷款交易信息.贷款.逾期31-60天未归还贷款本金","贷款交易信息.贷款.逾期61-90天未归还贷款本金",
                "贷款交易信息.贷款.逾期91-180天未归还贷款本金","贷款交易信息.贷款.逾期180天以上未归还贷款本金"};
        List<?> list = cisSearch.mutiSearchByFiled(includeFields,types,queryBuild,queryBuild2);
        System.out.println(list.size());*/

    }
}
