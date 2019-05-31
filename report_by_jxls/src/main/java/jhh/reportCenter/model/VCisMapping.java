package jhh.reportCenter.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author mugy
 * @Description: TODO
 * @date 2017/11/21
 * 上海资信映射实体
 */
@Getter
@Setter
public class VCisMapping extends BaseMapping {
    @JSONField(name = "gender")
    private byte gender;
    @JSONField(name = "birthday")
    private Date birthday;

    @JSONField(name = "信用报告头")
    private Object creditReportingHead;
    @JSONField(name = "个人身份信息")
    private Object personalIdentityInformation;
    @JSONField(name = "贷款申请信息")
    private Object loanApplicationInformation;
    @JSONField(name = "贷款交易信息")
    private Object loanTransactionInformation;
    @JSONField(name = "为他人担保信息")
    private Object guaranteeInformationOthers;
    @JSONField(name = "特殊交易信息")
    private Object specialTransactionInformation;
    @JSONField(name = "查询信息")
    private Object queryInformation;
    @JSONField(name = "个人声明信息")
    private Object personalStatementInformation;
    @JSONField(name = "资信提示信息")
    private Object creditInformation;


}
