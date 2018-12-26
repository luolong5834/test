package com.luolong.opensource.java.test.synchronize;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>可重入锁测试类</p>
 * 1,公平锁，非公平锁
 * @author luolong
 * @date 2018/12/25
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ReentrantLockTest {
    private static BoundedBuffer boundedBuffer = new BoundedBuffer();
    private static ReentrantLock reentrantLock1 = new ReentrantLock();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            consumerTest();
            chiliFactoryTest();
        }

    }

    public static void consumerTest() {
        //创建一个线程
        Runnable runnable = (() -> {
            try {
                boundedBuffer.take();
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        });
        Thread thread = new Thread(runnable);
        thread.start();
    }


    public static void chiliFactoryTest() {
        //创建一个线程
        Runnable runnable = (() -> {
            try {
                boundedBuffer.put("沙雕，辣死你");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public static class BoundedBuffer {
        final Lock lock = new ReentrantLock(true);
        final Condition notFull = lock.newCondition();
        final Condition notEmpty = lock.newCondition();

        final Object[] items = new Object[3];
        int putptr, takeptr, count;

        public void put(Object x) throws InterruptedException {
            reentrantLock1.lock();
            System.out.println(Thread.currentThread().getName() + "请求存储一个...");
            lock.lock();
            reentrantLock1.unlock();
            try {
                while (count == items.length) {
                    System.out.printf("储存线程(%s)开始等待...\n", Thread.currentThread().getName());
                    notFull.await();
                }

                items[putptr] = x;
                if (++putptr == items.length) putptr = 0;
                ++count;
                System.out.println(Thread.currentThread().getName() + "存储一个完成...");
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }

        public Object take() throws InterruptedException {
            reentrantLock1.lock();
            System.out.println(Thread.currentThread().getName() + "请求消费一个...");
            lock.lock();
            reentrantLock1.unlock();
            try {
                while (count == 0) {
                    System.out.printf("获取线程(%s)开始等待...\n", Thread.currentThread().getName());
                    notEmpty.await();
                }
                Object x = items[takeptr];
                if (++takeptr == items.length) takeptr = 0;
                --count;
                System.out.println(Thread.currentThread().getName() + "请求消费完成...");
                notFull.signal();
                return x;
            } finally {
                lock.unlock();
            }
        }

        public void printlnLockThread() {

        }
    }
}
