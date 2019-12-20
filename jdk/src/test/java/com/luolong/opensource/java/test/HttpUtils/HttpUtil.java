package com.luolong.opensource.java.test.HttpUtils;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/5/8
 */
@Slf4j
public class HttpUtil {

    public static String sendPost(String url, String data, String contentType, String encodeing) {

        OutputStream outputStream = null;
        HttpURLConnection conn = null;
        ByteArrayOutputStream bos = null;//接收数据
        InputStream inputStream = null;
        try {
            URL remoteUrl = new URL(url);
            conn = (HttpURLConnection) remoteUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
//			conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("connection", "close");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("charset", "UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setConnectTimeout(6000);
            conn.setReadTimeout(6000);
            conn.setRequestProperty("Content-type", contentType);
            // 发送数据
            byte[] datas = data.getBytes("UTF-8");
            datas = data.getBytes(encodeing);
            outputStream = conn.getOutputStream();
            outputStream.write(datas, 0, datas.length);
            outputStream.flush();
            // 读取返回数据
            inputStream = conn.getInputStream();

            bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[256];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            byte[] readBytes = bos.toByteArray();

            return new String(readBytes, "UTF-8");
        } catch (MalformedURLException e) {
            log.error("发送[{}]http post异常", url, e);
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            log.error("发送[{}]http post异常", url, e);
            throw new RuntimeException(e.getMessage());
        } finally {

            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                log.error("outputStream关闭异常", e);
            }
            try {
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                log.error("bos关闭异常", e);
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error("inputStream关闭异常", e);
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
