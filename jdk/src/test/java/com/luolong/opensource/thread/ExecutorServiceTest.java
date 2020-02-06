package com.luolong.opensource.java.test.thread;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.*;

/**
 * <p></p>
 * newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
 * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
 * newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
 * newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 *
 * @author luolong
 * @date 2018/12/24
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ExecutorServiceTest {
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
    @Test
    public void executeServiceShutdownTest() throws Exception {
        ExecutorService executorService;
        Future<Object> future = null;
        executorService = createFixedExecutorService(2, 100, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(400));
        boolean cancel;
        try {
            for (int i = 0; i < 2; i++) {
                Callable<Object> callable = CallableTest.createCallable();
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

    /**
     * 创建1个fixed线程池
     *
     * @param []
     * @return java.util.concurrent.ExecutorService
     * @author long.luo
     * @date 2018/12/24
     */
    public static ExecutorService createFixedExecutorService(Integer corePoolSize, Integer maximumPoolSize, Integer keepAliveTime, TimeUnit timeUnit, BlockingQueue<Runnable> workquene) {
        //等同于这个
        //return Executors.newFixedThreadPool(corePoolSize);
        //Executors.newCachedThreadPool(); 创建一个无线增大线程池
        //ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        //scheduledExecutorService.
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, workquene);
    }


}
