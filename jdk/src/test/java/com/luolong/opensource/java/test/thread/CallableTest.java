package com.luolong.opensource.java.test.thread;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.*;

/**
 * <p>callable测试类</p>
 *
 * @author luolong
 * @date 2018/12/21
 */
@SpringBootTest
public class CallableTest {
    /**
     * 创建一个callable线程
     * 1，lambda表达式创建
     * 2，lambda可以自己捕捉interruptException
     *
     * @param []
     * @return java.util.concurrent.Callable<java.lang.Object>
     * @author long.luo
     * @date 2018/12/21
     */
    public static Callable<Object> createCallable() {
        return (Callable<Object>) () -> {
            try {
                System.out.println("callable is running");
                TimeUnit.SECONDS.sleep(100000);
            } catch (InterruptedException e) {
                System.err.println("线程被中断...");
            }
            return -1;
        };
    }

    /**
     * 线程池有俩种关闭方法
     * 1，shutdown(),等待线程执行完关闭,拒绝后续task加入
     * 2，shutdownNow(),关闭正在执行的task,返回等待的task
     * 3, 任何不能响应中断的任务都不能被停止
     * 4,shutdown*（）操作实际调用的是{@code interrupt()}方法,所以判断是否完成应该用{@code isDone（）}来判断
     *
     * @param []
     * @return void
     * @author long.luo
     * @date 2018/12/24
     */
    private static void executeServiceShutdownTest() throws Exception {
        ExecutorService executorService;
        Future<Object> future = null;
        executorService = createFixedExecutorService();
        boolean cancel;
        try {
            for (int i = 0; i < 2; i++) {
                Callable<Object> callable = createCallable();
                future = executorService.submit(callable);
                System.out.println("睡眠3秒,尝试取消Callable");
                TimeUnit.SECONDS.sleep(3);
            }
        } finally {
            //关闭线程池
            List<Runnable> runnables = executorService.shutdownNow();
            //返回等待队列的task集合
            System.out.println(runnables.size());
            //判断是否取消
            while (true) {
                if (future.isCancelled()) {
                    System.out.println("task 已经取消");
                    break;
                } else if (future.isDone()) {
                    System.out.println("task 已经完成");
                    break;
                } else {
                    System.out.println("task 正在运行");
                }
            }
        }
    }

    private static ExecutorService createFixedExecutorService() {
        return new ThreadPoolExecutor(2, 500, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(400));
    }

    /**
     * 1,创建一个线程池 fixed
     * 2，使用完毕记得关闭线程池
     * 3,得考虑到future取消方法的参数
     *
     * @param [args]
     * @return void
     * @author long.luo
     * @date 2018/12/21
     */
    public static void main(String[] args) throws Exception {
        executeServiceShutdownTest();

    }
}
