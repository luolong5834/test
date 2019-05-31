package jhh.reportCenter.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author mugy
 * @Description: TODO
 * @date 2017/11/21
 */
@Getter
@Setter
public class TdMapping extends BaseMapping {
    @JSONField(name = "gender")
    private byte gender;
    @JSONField(name = "birthday")
    private Date birthday;
    @JSONField(name = "report_id")//报告编号
    private String reportId;
    @JSONField(name = "apply_time")//扫描时间
    private Date applyTime;
    @JSONField(name = "final_decision")//风险描述
    private String finalDecision;
    @JSONField(name = "final_score")//风险分数
    private Integer finalScore;
    @JSONField(name = "report_time")//报告时间
    private Date reportTime;
    @JSONField(name = "risk_items")//扫描出来的风险项
    private Object riskItems;
    @JSONField(name = "application_id")
    private String applicationId;//申请编号
    @JSONField(name = "address_detect")//归属地解析
    private Object addressDetect;
    @JSONField(name = "credit_score")//信用分
    private Integer credit_score;

}
