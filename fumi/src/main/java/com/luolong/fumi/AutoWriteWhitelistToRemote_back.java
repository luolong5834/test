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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * TODO
 *
 * @author longluo
 */
public class AutoWriteWhitelistToRemote_back {


    final String crfToken = "lXlqFzeuuRDHZ5nAeUePnMsvYGJowqMR7x0qgiagLyWxX3tZZuzqDaXGf8bfbBcy";
    final String cookieToken = "csrftoken=" + crfToken + ";" + "eid01=CmQCIl4NncoDdEr/A7j/Ag==";
    String sessionId = null;
    final int pageSize = 100;
    //final StringBuffer existId = new StringBuffer("");
    String newLine = "\n";
    final String sqlFile = "/Users/longluo/Documents/whiltelist_exist.sql";
    final String whiteFile = "/Users/longluo/Documents/whitelist.txt";
    String uri = "http://10.100.2.34:9123";
    long sqlTotal;
    boolean writeFile = true;
    boolean writeRemote = true;

    public void test() {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get("", sqlFile))) {
            //登陆
            login();
            //组装需要加入的白名单str
            String ids = getIdsFromFile(whiteFile);
            //组装已存在白名单查询sql
            String exists = getExistsIds(ids);
            String[] whites = ids.split(",");
            List repeats = new ArrayList<>();
            //查找重复的
            for (int i = 0; i < whites.length; i++) {
                for (int j = i + 1; j < whites.length; j++) {
                    if (whites[i].equalsIgnoreCase(whites[j])) {
                        repeats.add(whites[j]);
                    }
                }
            }
            final StringBuffer content = new StringBuffer("");
            content.append("INSERT INTO wlt_whitelist (`user_id`, `type`, `create_time`) VALUES").append(newLine);
            Arrays.stream(whites).filter(userId -> !exists.contains(userId))
                    .distinct().forEach(o -> {
                        content.append(String.format("(%s,'OPTION_OPEN',NOW()),", o)).append(newLine);
                    }
            );
            //删除最后的','符号 ;
            String sql = content.delete(content.length() - 2, content.length()).append(";").toString();
            if (writeFile) {
                bw.write(sql);
            }
            //提交sql
            if (writeRemote) {
                submitSql(sql);
            }

            long needWhite = Arrays.stream(whites).filter(userId -> !exists.contains(userId)).distinct().count();//真实要添加de白名单
            int existLen = StringUtils.isEmpty(exists) ? 0 : exists.split(",").length;
            sqlTotal = needWhite;
            boolean execute = whites.length == existLen + repeats.size() + needWhite;
            System.out.println(String.format("总数：%s, 已存在：%s, 重复：%s, 最终成功添加白名单：%s, 计算结果：%s", whites.length, existLen, repeats.size(), needWhite, execute));
            System.out.println("重复userId为:" + repeats);
            System.out.println("已存在userId:" + exists);
            System.out.println("生成的sql文件地址：" + sqlFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        String url = "http://10.100.2.34:9123/query/";
        HashMap<Object, Object> params = new HashMap<>();
        params.put("instance_name", "US-Fulldb");
        params.put("db_name", "wl_trade");
        params.put("sql_content", countSql);
        params.put("schema_name", null);
        params.put("tb_name", null);
        params.put("limit_num", "");

        String result = post(url, params);
        return result;
    }

    public String post(String urlparam, HashMap map) throws Exception {
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

    private String getExistsIds(String ids) throws Exception {
        final StringBuffer existId = new StringBuffer();
        Integer[] userIds = new Integer[1];
        userIds[0] = 0;
        final String[] array = ids.split(",");
        int total = (array.length + 100 - 1) / 100;
        //String exitsCountSql = "select count(*) from wlt_whitelist a where a.user_id  in (ids)  and type = 'OPTION_OPEN'";
/*
        String exitsPageSql = "select * from wlt_whitelist a where a.user_id  in (ids)  and type = 'OPTION_OPEN' and user_id > userId order by user_id   limit 100";
*/
        //exitsCountSql = exitsCountSql.replace("ids", ids);
        //exitsPageSql = exitsPageSql.replace("ids", ids);
        //String countResult = query(exitsCountSql);
        //JSONObject result = JSONObject.parseObject(countResult);
        //Integer total = Integer.parseInt(result.getJSONObject("data").getJSONArray("rows").getJSONArray(0).get(0).toString());
        ;
        //int totalTimes = (total + pageSize - 1) / pageSize;
        int cntTime = 0;
        while (cntTime < total -1) {
            String exitsPageSql = "select * from wlt_whitelist a where a.user_id  in (ids)  and type = 'OPTION_OPEN'    limit 100";

            exitsPageSql = exitsPageSql.replace("userId", userIds[0].toString());

            int end = cntTime * pageSize + 100;
            if (end >= array.length) {
                end = array.length;
            }
            final String[] copyOfRange = Arrays.copyOfRange(array, cntTime * pageSize, end + 1);
            exitsPageSql = exitsPageSql.replace("ids", StringUtils.join(copyOfRange, ","));
            String query = query(exitsPageSql);
            JSONArray results = JSONObject.parseObject(query).getJSONObject("data").getJSONArray("rows");
            System.out.println("循环:" + cntTime + "结果:" + results.size());
            cntTime++;


            if (results == null || results.size() == 0) {
                continue;
            }
            results.stream().forEach(o -> {
                JSONArray e = (JSONArray) o;
                userIds[0] = e.getInteger(1);
                existId.append(userIds[0]).append(",");
            });

        }
        //生成白名单sql
        if (StringUtils.isBlank(ids)) {
            System.out.println("没有需要加白名单的");
            return "";
        }
        return existId.length() > 0 ? existId.substring(0, existId.length() - 1) : "";

    }

    public String getIdsFromFile(String fileName) throws IOException {
        //获取查询白名单
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        String id;
        StringBuffer sb = new StringBuffer();
        while ((id = br.readLine()) != null) {
            sb.append(id).append(",");
        }
        String ids = sb.toString();
        ids = ids.substring(0, ids.length() - 1);
        return ids;
    }

    public void submitSql(String sql) throws Exception {
        if (sqlTotal > 0) {
            if (check(sql)) {
                final HashMap<Object, Object> params = new HashMap<>();
                params.put("workflow_name", String.format("期权增加%s白名单", sqlTotal));
                params.put("group_name", "交易组 ");
                params.put("instance_name", "US-Trade");
                params.put("db_name", "wl_trade");
                //params.put("notify_users", "luolong");
                params.put("sql_content", sql);
                params.put("is_backup", "False");
                params.put("run_date_start", "");
                params.put("run_date_end", "");
                params.put("workflow_id", "");
                params.put("upload", "");
                params.put("workflow_auditors", 1);
                String urlParam = uri + "/autoreview/";
                final String result = post(urlParam, params);
            }
        }
    }

    public boolean check(String sql) throws Exception {
        String url = uri + "/simplecheck/";
        final HashMap<Object, Object> map = new HashMap<>();
        map.put("sql_content", sql);
        map.put("instance_name", "US-Trade");
        map.put("db_name", "wl_trade");
        final String result = post(url, map);
        final JSONObject json = JSONObject.parseObject(result);
        return json.getString("status").equals("0");
    }

    public void sendDingTalk() throws ApiException {
        String dingTalkUrl = "https://oapi.dingtalk.com/robot/send?access_token=44ddb223dd7b51cab86f7474c4522b70db60226cb158d20f6e16fc28b3295617";
        DingTalkClient client = new DefaultDingTalkClient(dingTalkUrl);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent("测试文本消息。");
        request.setText(text);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setAtMobiles(Arrays.asList("15618641071"));
        request.setAt(at);

        /*request.setMsgtype("link");
        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        link.setMessageUrl("https://www.dingtalk.com/");
        link.setPicUrl("");
        link.setTitle("时代的火车向前开");
        link.setText("这个即将发布的新版本，创始人xx称它为“红树林”。\n" +
                "而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是“红树林");
        request.setLink(link);
*/
        /*request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle("杭州天气");
        markdown.setText("#### 杭州天气 @156xxxx8827\n" +
                "> 9度，西北风1级，空气良89，相对温度73%\n\n" +
                "> ![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png)\n"  +
                "> ###### 10点20分发布 [天气](http://www.thinkpage.cn/) \n");
        request.setMarkdown(markdown);*/
        OapiRobotSendResponse response = client.execute(request);

    }

    public static void main(String[] args) throws Exception {
        final AutoWriteWhitelistToRemote_back autoWriteWhitelistToRemote = new AutoWriteWhitelistToRemote_back();
        autoWriteWhitelistToRemote.test();
    }


}
