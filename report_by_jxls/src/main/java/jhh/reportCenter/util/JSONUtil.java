package jhh.reportCenter.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/1/28
 */
public class JSONUtil {
    public static JSONArray getJSONArray(JSONObject source, String key) {
        if (source == null) {
            return null;
        }
        String value = source.getString(key);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        if (!value.contains("["))
            value = "[" + value + "]";
        return JSONArray.parseArray(value);
    }
}
