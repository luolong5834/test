package com.luolong.opensource.java.test.native_test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * <p></p>
 * 1,Unsafe可以通过反射获取实例
 * 2,Unsafe可以用到并发，因为cpu的cas
 * 3,   Unsafe可以用到序列化
 *
 * @author luolong
 * @date 2019/1/14
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UnsafeTest {
    Unsafe unsafe;

    @Before
    public void init() throws NoSuchFieldException, IllegalAccessException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        unsafe = (Unsafe) f.get(null);
    }

    /**
     * 私有构造器用Unsafe去实例化
     *
     * @param []
     * @return void
     * @author long.luo
     * @date 2019/1/14
     */
    @Test
    public void privateConstructClassTest() throws InstantiationException, IllegalAccessException {
        /*
        Unsafe获取一个类的实例
         */
        A o = (A) unsafe.allocateInstance(A.class);
        System.out.println("name:" + o.getName());
        A o2 = A.class.newInstance(); // 反射
        System.out.println("name:" + o2.getName());
    }

}

class A {
    String name;

    public A() {
        this.name = "test";
    }

    public String getName() {
        return this.name;
    }
}