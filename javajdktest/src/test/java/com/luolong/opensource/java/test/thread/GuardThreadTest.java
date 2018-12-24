package com.luolong.opensource.java.test.thread;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2018/12/21
 */
@SpringBootTest
public class GuardThreadTest {
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

    public static void main(String[] args)  {
        createGuardThreadTest();
        //Thread.sleep(3000);
        System.out.println("结束");
    }

}
