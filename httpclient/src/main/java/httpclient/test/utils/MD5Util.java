package httpclient.test.utils;


import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

@Slf4j
public class MD5Util {

	/**
	 * MD5
	 * 签名
	 * @param paramter:请求参数字符串
	 * @return
	 * @author chennan
	 */
    public final static String MD5(String paramter) {
    	String sign="";
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = paramter.getBytes("UTF-8");
			// 使用MD5创建MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				// 将没个数(int)b进行双字节加密
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			sign=new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sign;
    }

}
