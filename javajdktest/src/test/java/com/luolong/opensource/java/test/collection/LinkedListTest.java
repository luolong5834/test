package com.luolong.opensource.java.test.collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;
import java.util.ArrayList;
import java.util.LinkedList;

/** 1，linkedlist的优劣势 fori循环特别慢
 *  2，linkedlist随机删除，如果数据量过100万，优势比较铭心啊，主要还是看插入的index的大小，
 *  3，顺序添加，顺序遍历，和arraylist速度差不多
 * <p></p>
 *
 * @author luolong
 * @date 2018/12/24
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class LinkedListTest {
    /**
     * 链表和arraylist fori遍历性能比较
     * fori循环linkedlist特别吃亏，特别慢
     * @param []
     * @return void
     * @author long.luo
     * @date 2018/12/24
     */
    @Test
    public  void linkedListAndArrayListIteratorCompator() {
        LinkedList linkedList = new LinkedList();
        ArrayList arrayList = new ArrayList<>();
        //add 1 万个 element both
        for (int i = 0; i < 100000; i++) {
            linkedList.add(i);
            arrayList.add(i);
        }
        //loop 2 list
        StopWatch stopWatch1 = new StopWatch();
        stopWatch1.start();
        for (int i = 0; i < linkedList.size(); i++) {
            System.out.println(linkedList.get(i));
        }
        stopWatch1.stop();

        StopWatch stopWatch2 = new StopWatch();
        stopWatch2.start();
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
        }
        stopWatch2.stop();

        System.out.println("时间:" + (stopWatch1.getTotalTimeMillis() - stopWatch2.getTotalTimeMillis()));

    }
    /**
     * linkedlist和arraylist foreach循环遍历
     * foreach还好，遍历时间差不多
     * @author long.luo
     * @date 2018/12/24
     * @param []
     * @return void
     */
    @Test
    public  void linkedListAndArrayListIteratorCompator2() {
        LinkedList linkedList = new LinkedList();
        ArrayList arrayList = new ArrayList<>();
        //add 1 万个 element both
        for (int i = 0; i < 10000; i++) {
            linkedList.add(i);
            arrayList.add(i);
        }
        //loop 2 list
        StopWatch stopWatch1 = new StopWatch();
        stopWatch1.start();
        linkedList.forEach(o -> {
            System.out.println(o);
        });
        stopWatch1.stop();
        StopWatch stopWatch2 = new StopWatch();
        stopWatch2.start();
        arrayList.forEach(o -> {
            System.out.println(o);
        });
        stopWatch2.stop();

        System.out.println("时间:" + (stopWatch1.getTotalTimeMillis() - stopWatch2.getTotalTimeMillis()));
    }


    /**
     * linkedlist和arraylist foreach循环遍历
     * foreach还好，遍历时间差不多
     * @author long.luo
     * @date 2018/12/24
     * @param []
     * @return void
     */
    @Test
    public  void linkedListAndArrayListIteratorCompator3() {
        LinkedList linkedList = new LinkedList();
        ArrayList arrayList = new ArrayList<>();
        //add 1 万个 element both
        for (int i = 0; i < 1000000; i++) {
            linkedList.add(i);
            arrayList.add(i);
        }
        //loop 2 list
        StopWatch stopWatch1 = new StopWatch();
        stopWatch1.start();
        for (Object o : linkedList) {
            System.out.println(o);
        }
        stopWatch1.stop();

        StopWatch stopWatch2 = new StopWatch();
        stopWatch2.start();
        for (Object o : arrayList) {
            System.out.println(o);
        }
        stopWatch2.stop();
        System.out.println("时间:" + (stopWatch1.getTotalTimeMillis() - stopWatch2.getTotalTimeMillis()));
    }


    /**
     * linkedlist和arraylist 顺序插入
     * 1，顺序插入时间复杂度差不多
     * @author long.luo
     * @date 2018/12/24
     * @param []
     * @return void
     */
    @Test
    public  void linkedListAndArrayListAddCompator() {
        LinkedList linkedList = new LinkedList();
        ArrayList arrayList = new ArrayList<>();
        //add 1 万个 element both
        StopWatch stopWatch1 = new StopWatch();
        stopWatch1.start();
        for (int i = 0; i < 1000000; i++) {
            linkedList.add(i);
        }
        stopWatch1.stop();

        StopWatch stopWatch2 = new StopWatch();
        stopWatch2.start();
        for (int i = 0; i < 1000000; i++) {
            arrayList.add(i);
        }
        stopWatch2.stop();
        System.out.println("插入时间对比:" + (stopWatch1.getTotalTimeMillis() - stopWatch2.getTotalTimeMillis()));
    }

    /**
     * linkedlist和arraylist 随机插入
     * 1，
     * @author long.luo
     * @date 2018/12/24
     * @param []
     * @return void
     */
    @Test
    public  void linkedListAndArrayListRandomAddCompator() {
        LinkedList linkedList = new LinkedList();
        ArrayList arrayList = new ArrayList<>();
        //add 1 万个 element both
        for (int i = 0; i < 500000; i++) {
            linkedList.add(i);
            arrayList.add(i);
        }
        StopWatch stopWatch1 = new StopWatch();
        stopWatch1.start();
        for (int i = 0; i < 1000000; i++) {
            linkedList.add(250000,2);
        }
        stopWatch1.stop();

        StopWatch stopWatch2 = new StopWatch();
        stopWatch2.start();
        for (int i = 0; i < 1000000; i++) {
            arrayList.add(250000,2);
        }
        stopWatch2.stop();
        System.out.println("随机插入时间对比:" + (stopWatch1.getTotalTimeMillis() - stopWatch2.getTotalTimeMillis()));
    }
}
