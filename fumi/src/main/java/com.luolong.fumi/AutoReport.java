package com.luolong.fumi;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.time.ZoneId;
import java.util.*;

/**
 * TODO
 *
 * @author longluo
 */
public class AutoReport {

    final static String myRobotUrl = "https://oapi.dingtalk.com/robot/send?access_token=60c1d673bcf5dff2fbac1c1235d9d48b3ba4b845c5b6c1233e6ff46f1bf75626";
    final static String otherRobotUrl = "https://oapi.dingtalk.com/robot/send?access_token=44ddb223dd7b51cab86f7474c4522b70db60226cb158d20f6e16fc28b3295617";
    final String crfToken = "lXlqFzeuuRDHZ5nAeUePnMsvYGJowqMR7x0qgiagLyWxX3tZZuzqDaXGf8bfbBcy";
    final String cookieToken = "csrftoken=" + crfToken + ";" + "eid01=CmQCIl4NncoDdEr/A7j/Ag==";
    String sessionId = null;
    String queryUsersSql = "SELECT count(1) as 开户,date_format(option_open_time,'%Y-%m-%d') as option_open_time from wlt_securities_account where   option_open_time > '2019-12-01' group by date_format(option_open_time,'%Y-%m-%d') with rollup limit 100;";
    String queryTradeUserSql = "SELECT count(DISTINCT securities_account_id) 交易用户数,date_format(create_time,'%Y-%m-%d') 日期\n" +
            "FROM wlt_stock_order o\n" +
            "WHERE o.ticker_type = 7\n" +
            "  AND create_time > '2019-12-01 00:00:00'\n" +
            "GROUP BY date_format(create_time,'%Y-%m-%d')  with rollup limit 100;";
    String queryTradeOrders = "SELECT count(1) as 下单数, sum(quantity) 合约数, if(status = 'FILLED','成交数','未成交') 状态,date_format(create_time,'%Y-%m-%d') 日期\n" +
            "FROM wlt_stock_order o\n" +
            "WHERE o.ticker_type = 7\n" +
            "  AND create_time > '2019-12-01 00:00:00'\n" +
            "GROUP BY if(status = 'FILLED','成交数','未成交'),date_format(create_time,'%Y-%m-%d')  with rollup limit 100;";
    String format = "%-20s";
    String format1 = "%-17s";

    public String test() throws Exception {
        login();
        final String query = query(queryUsersSql);
        System.out.println("已经查出1");
        final JSONArray data = JSONObject.parseObject(query).getJSONObject("data").getJSONArray("rows");
        StringBuffer result = new StringBuffer();
        String newLine = "\n";
        String lineFmt = "> ";
        String titleFmt = "## ";
        String secFmt = "- ";
        result.append(titleFmt)
                .append("期权统计")
                .append(newLine);
        result.append(secFmt)
                .append("开户统计如下")
                .append(newLine);
        result.append(lineFmt)
                .append(String.format(format1, "开户人数"))
                .append(String.format(format1, "日期"))
                .append(newLine);
        for (int i = 0; i < data.size(); i++) {
            JSONArray a = (JSONArray) data.get(i);
            if (i == data.size() - 1) {
                result.append(lineFmt)
                        .append(String.format(format, a.getInteger(0)))
                        .append(String.format(format1, "合计"))
                        .append(newLine);
            } else {
                result.append(lineFmt)
                        .append(String.format(format, a.getInteger(0)))
                        .append(String.format(format1, a.getString(1)))
                        .append(newLine);
            }
        }

        final String query1 = query(queryTradeUserSql);
        System.out.println("已经查出2");
        final JSONArray data1 = JSONObject.parseObject(query1).getJSONObject("data").getJSONArray("rows");
        StringBuffer result1 = new StringBuffer();
        result1.append(secFmt)
                .append("交易用户统计")
                .append(newLine);
        result1.append(lineFmt)
                .append(String.format(format1, "交易用户数(已去重)")).append(String.format(format1, "日期")).append(newLine);
        for (int i = 0; i < data1.size(); i++) {
            JSONArray a = (JSONArray) data1.get(i);
            if (i == data1.size() - 1) {
                result1.append(lineFmt)
                        .append(String.format(format, a.getInteger(0)))
                        .append(String.format(format1, "合计"))
                        .append(newLine);
            } else {
                result1.append(lineFmt)
                        .append(String.format(format, a.getInteger(0)))
                        .append(String.format(format1, a.getString(1)))
                        .append(newLine);
            }
        }
        final String query2 = query(queryTradeOrders);
        System.out.println("已经查出3");
        final JSONArray data2 = JSONObject.parseObject(query2).getJSONObject("data").getJSONArray("rows");
        StringBuffer result2 = new StringBuffer();
        result2.append(newLine)
                .append(secFmt)
                .append("订单/合约统计")
                .append(newLine);
        result2.append(lineFmt).append(String.format(format1, "成交订单数"))
                .append(String.format(format1, "成交合约数"))
                .append(String.format(format1, "状态"))
                .append(String.format(format1, "日期")).append(newLine);
        for (int i = 0; i < data2.size(); i++) {
            JSONArray a = (JSONArray) data2.get(i);
            if (a.getString(3) == null) {
                result2.append(lineFmt)
                        .append(String.format(format, a.getInteger(0)))
                        .append(String.format(format, a.getInteger(1)))
                        .append(String.format(format1, a.getString(2)))
                        .append(String.format(format1, "合计")).append(newLine);
            } else {
                result2.append(lineFmt)
                        .append(String.format(format, a.getInteger(0)))
                        .append(String.format(format, a.getInteger(1)))
                        .append(String.format(format1, a.getString(2)))
                        .append(String.format(format1, a.getString(3))).append(newLine);
            }
        }
        System.out.println(result.append(newLine).append(result1).append(newLine).append(result2));
        return result.append(newLine).append(result1).append(newLine).append(result2).toString();
    }

    private void login() throws Exception {
        String userName = "luolong";
        String password = "Free_dom123";
        String urlParam = "http://10.100.2.34:9123/authenticate/";
        HashMap<Object, Object> params = new HashMap<>();
        params.put("password", password);
        params.put("username", userName);
        //调用
        post(urlParam, params);

    }


    public String query(String countSql) throws Exception {
        int count = 0;
        String result = null;
        try {
            String url = "http://10.100.2.34:9123/query/";
            HashMap<Object, Object> params = new HashMap<>();
            params.put("instance_name", "US-Fulldb");
            params.put("db_name", "wl_trade");
            params.put("sql_content", countSql);
            params.put("schema_name", null);
            params.put("tb_name", null);
            params.put("limit_num", "");

            result = post(url, params);
            count = 0;
            while ((result == null || JSONObject.parseObject(result).getInteger("status") != 0) && count < 4) {
                count++;
                System.out.println("重试一次:" + countSql);
                result = post(url, params);
                Thread.sleep(2000);
            }
            return result;
        } catch (Exception e) {
            while ((result == null || JSONObject.parseObject(result).getInteger("status") != 0) && count < 4) {
                count++;
                System.out.println("重试一次:" + countSql);
                result = query(countSql);
                Thread.sleep(2000);
            }
        }
        return result;
    }

    public String post(String urlparam, HashMap map) throws Exception {
        //System.out.println(map);
        //构建连接对象
        URL url = new URL(urlparam);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset:utf-8");
        conn.setRequestProperty("Cookie", cookieToken);
        conn.setRequestProperty("X-CSRFToken", crfToken);
        conn.setRequestProperty("csrfmiddlewaretoken", "PLe7Oma90Pd14Rh5GokRbp1i6OlQxKW0Rj2xF2wyZiDUP5bCZR7LD5iDQCederpM");
        conn.setUseCaches(true);
        if (StringUtils.isNotBlank(sessionId)) {
            conn.setRequestProperty("Cookie", sessionId + ";" + cookieToken);

        }
        conn.connect();
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        if (map != null) {
            StringBuffer sb = new StringBuffer();
            map.forEach((k, v) -> {
                try {
                    if (v == null) {
                        sb.append(k).append("=");
                    }
                    if (v != null) {
                        sb.append(k).append("=").append(URLEncoder.encode(v.toString(), "utf-8")).append("&");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            });
            sb.substring(0, sb.length() - 1);
            //.out.println(sb);
            out.writeBytes(sb.toString());
        }

        out.flush();
        out.close();
        InputStream inputStream = conn.getInputStream();
        byte[] b = new byte[1024];
        StringBuffer str = new StringBuffer();
        int length;
        while ((length = inputStream.read(b)) != -1) {
            str.append(new String(b, 0, length, Charset.forName("UTF-8")));
        }

        inputStream.close();
        String result = str.toString();
        String session_value = conn.getHeaderField("Set-Cookie");
        String[] sessionIds = session_value.split(";");
        sessionId = sessionIds[0];
        conn.disconnect();
        return result;
    }


    public void sendDingTalk(String webHook, String msg, String type, String atPeople) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient(webHook);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        if (type.equalsIgnoreCase("text")) {
            request.setMsgtype(type);
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent(msg);
            request.setText(text);
            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
            at.setAtMobiles(Arrays.asList(atPeople));
            request.setAt(at);
        } else if (type.equalsIgnoreCase("markdown")) {
            request.setMsgtype(type);
            OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
            markdown.setTitle("每日期权统计(12月1到至今。");
            markdown.setText(msg);
            request.setMarkdown(markdown);
            request.setAt(atPeople);
        } else if (type.equalsIgnoreCase("link")) {
            request.setMsgtype(type);
            OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
            link.setMessageUrl("https://www.dingtalk.com/");
            link.setPicUrl("");
            link.setTitle("时代的火车向前开");
            link.setText("这个即将发布的新版本，创始人xx称它为“红树林”。\n" +
                    "而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是“红树林");
            request.setLink(link);
            request.setMsgtype("markdown");
        }

        final OapiRobotSendResponse execute = client.execute(request);
        System.out.println(execute);


    }

    public static void main(String[] args) throws Exception {
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                final AutoReport execute = new AutoReport();
                try {
                    final String result = execute.test();//
                    execute.sendDingTalk(otherRobotUrl, "期权统计信息请查看", "text", "13775192177");
                    execute.sendDingTalk(otherRobotUrl, result, "markdown", "13775192177");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        TimerTask mytask = new TimerTask() {
            @Override
            public void run() {
                final AutoReport execute = new AutoReport();
                final String result;
                try {
                    result = execute.test();
                    execute.sendDingTalk(myRobotUrl, "期权统计信息请查看", "text", "15618641071");
                    execute.sendDingTalk(myRobotUrl, result, "markdown", "15618641071");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        final Calendar myInstance = Calendar.getInstance(TimeZone.getDefault());
        myInstance.set(2020, 0, 8, 10, 16);
        //timer.schedule(mytask, new Date(), 1000 * 60 * 60 * 24);

        //7
        final Calendar instance = Calendar.getInstance(TimeZone.getDefault());
        instance.set(2020, 0, 9, 15, 42);
        timer.schedule(timerTask, instance.getTime(), 1000 * 60 * 60 * 24);


        /*final Calendar instance = Calendar.getInstance(TimeZone.getDefault());
        instance.set(2020, 0, 7, 15, 30);

        timer.schedule(timerTask, instance.getTime(), 1000 * 60 * 60 * 24);*/
        //execute.sendDingTalk();

    }

}

