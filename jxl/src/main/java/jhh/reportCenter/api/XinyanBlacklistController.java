package jhh.reportCenter.api;

import com.alibaba.fastjson.JSONObject;
import com.jinhuhang.risk.xinyan.tanzhen.TanZhenService;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/report/xinyan/blacklist")
@Slf4j
public class XinyanBlacklistController {

    @RequestMapping("")
    public String index() {
        return "report/xinyan/xinyan_blacklist_report";
    }

    @PostMapping("/search")
    @ResponseBody
    public Object searchReport(String token, String name, String idcard, String phone) {
        log.info("参数为：name:{},icard:{},phone:{}", name, idcard, phone);
        TanZhenService tanZhenService = new TanZhenService();
        String result = "";
        String code = "9";
        try {
            result = tanZhenService.getReport(token, idcard, phone, name);
            if (StringUtils.isNotBlank(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                //code码对应的值
                // 0：风险探针A(逾期未还款)
                //1：无法确认
                //2：空值未知
                //9：其他异常
                code = jsonObject.getJSONObject("data").getString("code");
            }
        } catch (Exception e) {
            log.error("调用新颜反欺诈服务出现错误：原因:{}", e);
            return new Result.ResultBuilder().code("fail");
        }

        return new Result.ResultBuilder().code("success").isBlacklistCode(code).reportData(result).build();
    }
}

@Data
@Builder
class Result {
    String code;//success,fail
    String isBlacklistCode;
    String reportData;
}