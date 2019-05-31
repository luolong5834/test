package jhh.reportCenter.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/1/24
 */
@Getter
@Setter
public class SxdReportDto {
    /**
     * 姓名
     */
    private String personalName;
    private String cardNo;
    private String phone;
    /**
     * 合同状态
     */
    private String borrowStatus;
    /**
     * 通讯录个数
     */
    private String tongxuluCount;
    /**
     * 地址变动情况
     */
    private String cisAddrStatus;
    /**
     * 工作地点变动情况
     */
    private String cisWorkAddrStatus;
    /**
     * 贷款笔数
     */
    private String cisDaikuanCount;
    /**
     * 逾期31-60天未归还贷款本金
     */
    private String cisYuqi31_60;
    /**
     * 逾期61-90天未归还贷款本金
     */
    private String cisYuqi61_90;
    /**
     * 逾期91-180天未归还贷款本金
     */
    private String cisYuqi91_180;
    /**
     * 逾期180天以上未归还贷款本金
     */
    private String cisYuqi180_;
    /**
     * 身份证命中高风险关注名单
     */
    private String tdCardLevel;
    /**
     * 手机号命中虚假号码库
     */
    private String tdPhoneLevel;

}
