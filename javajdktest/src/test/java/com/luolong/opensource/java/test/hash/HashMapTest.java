package com.luolong.opensource.java.test.hash;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 1,遍历map.keyset()针对于key
 * 2,遍历map.entryset()针对于要拿key获取value
 * 3,map的默认初始容量为16，负载引资因子为0.75 超过12，会扩大到32（原来的俩倍）
 * 4,hashmap扩容时涉及到数组到新数组的复制，所以建立map要谨慎
 * </p>
 *
 * @author luolong
 * @date 2019/1/7
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class HashMapTest {

    @Test
    public void entryTest() {
        Map map1 = new HashMap() {
            {
                put(1, "1123");
                put(2, "1234");
                put(3, "1234");
                put(4, "12345");
            }
        };
        map1.keySet();
        map1.entrySet();
    }

    @Test
    public void loadfactor() {

        //create loadfactor is 0.75 ,initCapatury is 16,
        HashMap map = new HashMap(16, 0.75F);
        for (int i = 0; i < 12; i++) {
            map.put(i, i);
        }
        map.put(1000, 1);
        System.out.println(map.entrySet().size());
    }

}
