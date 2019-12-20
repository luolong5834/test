package com.luolong.opensource.java.test.test;

import com.luolong.opensource.java.test.ServiceTest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/1/29
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {
    @Autowired
    ServiceTest serviceTest;

    @org.junit.Test
    public void test() {
        System.out.println("fuck");
    }
}
