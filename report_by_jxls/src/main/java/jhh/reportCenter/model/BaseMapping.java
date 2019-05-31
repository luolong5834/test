package jhh.reportCenter.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author tangxd
 * @Description: TODO
 * @date 2017/11/21
 */
@Getter
@Setter
public abstract class BaseMapping extends AbstractMapping<String> {
    @JSONField(name = "product_id")
    private String productId;
    @JSONField(name = "card_no")
    private String cardNo;
    @JSONField(name = "phone")
    private String phone;
    @JSONField(name = "contact_path")
    private String contactPath;
    @JSONField(name = "personal_name")
    private String personalName;
    @JSONField(name = "create_time")
    private Date createTime = new Date();
    @JSONField(name = "risk_org_code")
    private String riskOrgCode;
}
