package com.luolong.test.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.annotation.Retryable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/3/25
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {
    @Test
    public void test1() {
        test();
    }

    @Retryable
    public void test() {
        System.out.println("luolong");
    }
    @Test
    public void addTest() {
        System.out.println(11);

    }


}
