package com.luolong.test.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

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
    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void addTest(){
        String uuid;
        for (int i = 0; i < 10; i++) {
            redisTemplate.opsForValue().set("global:uuid"+i, UUID.randomUUID().toString());
        }

    }
}
