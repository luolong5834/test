package com.luolong.opensource.java.test.lambda;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2018/12/26
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class LambdaTest {

    public static void main(String[] args) {
        List<Object> userList = initList(10000);
        for (int i = 1; i < 11; i++) {
            System.out.println("--------------------第" + i + "次");
            long t1 = System.nanoTime();
            testLambda(userList);
            long t2 = System.nanoTime();
            testForeach(userList);
            long t3 = System.nanoTime();
            System.out.println("lambda---" + (t2 - t1) / 1000 + "μs");
            System.out.println("增强for--" + (t3 - t2) / 1000 + "μs");
        }
    }

    /**
     * 增强for测试
     * @param userList
     */
    private static void testForeach(List<Object> userList) {
        for (Object user : userList) {
            user.hashCode();
        }
    }


    /**
     * lambda forEach测试
     * @param userList
     */
    private static void testLambda(List<Object> userList) {
        userList.forEach(user -> user.hashCode());
    }

    /**
     * 初始化测试集合
     * @param size
     * @return
     */
    private static List<Object> initList(int size) {
        List<Object> userList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            userList.add(i);
        }
        return userList;
    }


}
