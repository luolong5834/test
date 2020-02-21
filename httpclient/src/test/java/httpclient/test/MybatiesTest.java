package java.httpclient.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.annotation.Retryable;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/4/8
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatiesTest {

    @Retryable
    public void test() {
        System.out.println("luolong");
        System.out.println(111);
        int i= 1/0;
    }

    @Test
    public void test1() {
        test();
    }
}
