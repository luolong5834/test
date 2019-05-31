package jhh.reportCenter.convert;

import jhh.reportCenter.model.BaseMapping;

/**
 * @author tangxd
 * @Description: 原报文转换成ES对象模型
 * @date 2017/11/21
 */
public interface Converter {
    BaseMapping convert(String source);
}
