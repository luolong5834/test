package com.luolong.opensource.java.test.httpClient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/5/6
 */
public class HttpClientTest {


    public static void main(String[] args) throws Exception {
        Map<String, String> resultMap = new HashMap<String, String>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        String md52 = "1111";
        File file = new File("C:\\Users\\luolong\\Desktop\\员工绩效考核书-罗龙-04.docx");
        FileInputStream fileInputStream = new FileInputStream(file);
        System.out.println("Md5后" + md52);
        try {
            HttpPost httpPost = new HttpPost("https://openapi.qiyuesuo.me/remote/contract/createbyfile");
            long time = new Date().getTime();
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            NameValuePair nameValuePair = new NameValuePair() {
                @Override
                public String getName() {
                    return null;
                }

                @Override
                public String getValue() {
                    return null;
                }
            }
            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(111);
            //httpPost.setHeader("Content-Type", "multipart/form-data");

            httpPost.setHeader("x-qys-open-signature", md52);
            httpPost.setHeader("x-qys-open-accesstoken", "mkQqnOmYLn");
            httpPost.setHeader("x-qys-open-timestamp", "0");
            builder.setCharset(java.nio.charset.Charset.forName("UTF-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            String fileName = null;
	        /*MultipartFile multipartFile = null;
	        for (int i = 0; i < multipartFiles.size(); i++) {
            multipartFile = multipartFiles.get(i);*/
            fileName = "file";
            builder.addBinaryBody("file", fileInputStream, ContentType.MULTIPART_FORM_DATA, "1111");
            //决中文乱码
            ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
           /* for (Map.Entry<String, Object> entry : params.entrySet()) {
                if(entry.getValue() == null)
                    continue;
                // 类似浏览器表单提交，对应input的name和value
                System.out.println("Key{}"+entry.getKey()+"Value{}"+entry.getValue().toString());
                builder.addTextBody(entry.getKey(), entry.getValue().toString(), contentType);
            }*/
            builder.addTextBody("111", "2222", contentType);
            ContentType.APPLICATION_FORM_URLENCODED
            HttpEntity entity = builder.build();
            System.out.println("entity ----------" + entity.toString());
            httpPost.setEntity(entity);
            CloseableHttpResponse response = httpClient.execute(httpPost);// 执行提交


            // 设置连接超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(6000)
                    .setConnectionRequestTimeout(6000).setSocketTimeout(6000).build();
            httpPost.setConfig(requestConfig);

            HttpEntity responseEntity = response.getEntity();
            System.out.println(response.getStatusLine().getStatusCode() + "-------------");
            System.out.println(responseEntity + "+++++++++");
            resultMap.put("scode", String.valueOf(response.getStatusLine().getStatusCode()));
            resultMap.put("data", "");
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, java.nio.charset.Charset.forName("UTF-8"));
                resultMap.put("data", result);
            }
        } catch (Exception e) {
            resultMap.put("scode", "error");
            resultMap.put("data", "HTTP请求出现异常: " + e.getMessage());

            Writer w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));

            System.out.println("HTTP请求出现异常: " + w.toString());
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(resultMap);
    }


    /**
     *这是外面传的参数
     */
/*
    Map<String, Object> data = new HashMap<>();
			    data.put("subject", "契约所合同名称");
			    data.put("expireTime", "2019-06-12");
			    data.put("docName", "compact");
    Date date =new Date();
    long time = date.getTime();
			    System.out.println(time);
    String str= appToken+appSecret+0;
			    System.out.println(str);
    Map<String, String> resultDataMap = HttpUtil.httpPostRequest2(url,pdfFile, "合同", data, -1,str);*/


}
