package com.luolong.opensource.java.test.thread;

import com.luolong.opensource.java.test.synchronize.ReentrantLockTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * <p></p>
 * 1,守护线程是所有用户线程运行完毕Java JVM虚拟机才会退出
 * 2,如果有线程是正在运行，或正在
 *
 * @author luolong
 * @date 2018/12/21
 */
@SpringBootTest
public class GuardThreadTest {
    static ReentrantLockTest.BoundedBuffer boundedBuffer = new ReentrantLockTest.BoundedBuffer();

    public static void createThread() {
        //创建一个线程
        Runnable runnable = (() -> {
            try {
                while (true) {
                    System.out.println("沙雕，辣死你...");
                    boundedBuffer.put("沙雕，辣死你");
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread = new Thread(runnable, "threadTest");
        thread.start();
    }

    /**
     * 创建一个守护线程
     * 守护线程
     *
     * @param
     * @return void
     * @author long.luo
     * @date 2018/12/21
     */
    private static void createGuardThreadTest() {
        Thread threadGurad = new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    System.out.println("守护线程正在运行...");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void interrupt() {
                //这个方法不会被执行到，因为如果jvm没有用户线程，JVM会自动退出
                super.interrupt();
                System.out.println("守护线程interrupt...");
            }
        };
        Thread thread1 = new Thread(threadGurad);
        thread1.setDaemon(true);
        thread1.start();
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("main_1");
        createGuardThreadTest();
        createThread();
        //Thread.sleep(3000);
        System.out.println("结束");
    }


}
