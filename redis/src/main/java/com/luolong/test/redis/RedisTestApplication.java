package com.luolong.test.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/3/25
 */
@SpringBootApplication
@EnableRetry
public class RedisTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisTestApplication.class, args);
    }
}
