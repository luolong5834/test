package jhh.reportCenter.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class BizReportSourceSxdDto {
    /**
     *
     */
    private Integer id;

    /**
     * 合同编号
     */
    private Integer borrowId;

    /**
     * 合同状态
     */
    private String borrowStatus;

    /**
     * 资产端产品编号
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;
    /**
     * 姓名
     */
    private String personalName;
    /**
     * 身份证号码
     */
    private String idcardNo;

    /**
     * 手机号码
     */
    private String phone;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private String createBy;

    /**
     *
     */
    private Date updateTime;

    /**
     *
     */
    private String updateBy;

    /**
     * 通讯录个数
     */
    private String tongxuluCount;

    private String beginCreateTime;

    private String endCreateTime;


}