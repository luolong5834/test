package com.luolong.opensource.java.test.httpClient;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/5/8
 */
public class Test {

    public String httpPost(Map<String, String> params, String url) throws UnsupportedEncodingException {
        List<NameValuePair> list = new LinkedList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue());
            list.add(nvp);
        }
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "utf-8");

        return null;
    }
}
