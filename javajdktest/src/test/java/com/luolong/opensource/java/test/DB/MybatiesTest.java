package com.luolong.opensource.java.test.DB;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/4/8
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatiesTest {

    public static void main(String[] args) throws IOException {
        String report = "";
        FileReader fr = new FileReader("E:\\ideaWorkspace\\test\\javajdktest\\src\\test\\resouces/report/jxl.json");

        BufferedReader br = new BufferedReader(fr);
        String line = "";
        while ((line = br.readLine()) != null) {
            report += line;
        }

        String expresion = "(array)application_check.(map)app_point:user_name.(map)check_points.(keyValue)key_value";
        String[] jsonElement = expresion.split("\\.");
        String result = null;
        JSONObject jsonObject = JSONObject.parseObject(report);
        JSONArray jsonArray = null;
        for (String ele : jsonElement) {
            if (ele.contains("(array)")) {
                ele = ele.replace("(array)", "");
                jsonArray = jsonObject.getJSONArray(ele);
            } else if (ele.contains("(map)")) {
                ele = ele.replace("(map)", "");
                String eleValue = "";
                if (ele.contains(":")) {
                    String eles[] = ele.split("\\:");
                    ele = eles[0];
                    eleValue = eles[1];
                    if (jsonArray != null) {
                        for (Object o : jsonArray) {
                            jsonObject = (JSONObject) o;
                            if (eleValue.equals(jsonObject.getString(ele))) {
                                break;
                            }
                        }
                    }

                } else {
                    jsonObject = jsonObject.getJSONObject(ele);
                }

            } else if (ele.contains("(keyValue)")) {
                ele = ele.replace("(keyValue)", "");
                result = jsonObject.getString(ele);
                break;
            }
        }
        System.out.println("结果:"+result);
    }
}
