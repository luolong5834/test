package jhh.reportCenter;

/**
 * @author tangxd
 * @Description: TODO
 * @date 2017/11/22
 */
public class Consts {
    public final static String JXL_NAME = "jxl";//聚信立
    public final static String ZM_NAME = "zhima";//芝麻
    public final static String TD_NAME = "tongdun";//同盾
    public final static String VCIS_NAME = "cis";//上海资信
    public final static String YXSHARE_NAME = "yxshare";//宜信共享
    public final static String BAI_QI_SHI_NAME = "baiQiShi";//白骑士
    public final static String BAI_QI_SHI_PHONE_NAME = "bqsDevice";//白骑士设备
    public final static String GJJ_NAME = "gjj";//公积金
    public final static String JD_NAME = "JD";//京东
    public final static String CC_NAME = "CC";//信用卡
    public final static String TAOBAO_NAME = "taobao";//淘宝
    public final static String CHSI_NAME = "chsi";//学信网
    public final static String MIGUAN_NAME = "miguan";//蜜罐

    public final static int RESPONSE_ERROR = 0;
    public final static int RESPONSE_SUCCESS = 1;

    public final static String IDX_NAME = "jhh-risk-idx-latest";
    public final static String IDX_ALIAS_OLD = "jhh-risk-latest-v1";
    //public final static String IDX_ALIAS = "jhh-risk-latest-v1";//存储索引
    //public final static String IDX_ALIAS = "jhh-risk-latest-v2";//现在指定的存储索引
    public final static String IDX_ALIAS = "jhh-risk-idx-latest-v20180403";//蜜罐索引修改

    public final static String IDX_OLD_NAME = "jhh-risk-idx-old";
    public final static String IDX_OLD_ALIAS = "jhh-risk-idx-old-v1";
    public final static String IDX_SOURCE_NAME = "jhh-risk-idx-source";
    public final static String IDX_SOURCE_ALIAS = "jhh-risk-idx-source-v1";

    public final static String[] HISTORY_INDEX_MAPPING = {IDX_ALIAS, "jhh-risk-idx-latest"};//历史存储索引映射
    public final static String[] SEARCH_INDEX_MAPPING = {IDX_SOURCE_ALIAS, IDX_ALIAS, IDX_ALIAS_OLD, IDX_OLD_ALIAS, "jhh-risk-idx-latest"};
}
