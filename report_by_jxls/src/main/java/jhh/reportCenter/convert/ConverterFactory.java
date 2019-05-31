package jhh.reportCenter.convert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tangxd
 * @Description: TODO
 * @date 2017/11/21
 */
public class ConverterFactory {
    private final static Map<String, Converter> CONVERTERS = new ConcurrentHashMap<>();

    public static Converter getConverter(String name) {
        if (CONVERTERS.containsKey(name)) {
            return CONVERTERS.get(name);
        }
        throw new RuntimeException("不存在" + name + "对应的转换器");
    }

    static void registerConverter(String name, Converter converter) {
        if (CONVERTERS.containsKey(name)) {
            return;
        }
        CONVERTERS.put(name, converter);
    }

}
