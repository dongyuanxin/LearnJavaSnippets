package thread.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1、线程池种类：FixedThreadPool（固定大小，最常用）、CachedThreadPool（线程数根据任务动态调整的线程池）、ScheduledThreadPool（定时触发的）
 * 2、FixedThreadPool：最常用
 * 3、CachedThreadPool：相当于new ThreadPoolExecutor(
 *         min, max,
 *         60L, TimeUnit.SECONDS,
 *         new SynchronousQueue<Runnable>())
 * 4、ScheduledThreadPool：
 *  - 注意FixedRate和FixedDelay的区别。FixedRate是指任务总是以固定时间间隔触发，不管任务执行多长时间；FixedDelay是指，上一次任务执行完毕后，等待固定的时间间隔，再执行下一次任务
 *  - 比 Timer 更先进
 */
public class ThreadPollDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 6; i++) {
            es.submit(new ThreadPollDemoTask("" + i));
        }
        // 关闭线程池的时候，它会等待正在执行的任务先完成，然后再关闭
        es.shutdown();
        // shutdownNow()会立刻停止正在执行的任务，awaitTermination()则会等待指定的时间让线程池关闭。

        // awaitTermination 阻塞当前线程等待线程池所有线程结束，只是状态检查（都完成返回true，否则是false），在 shutdown 后调用
        while (!es.awaitTermination(1, java.util.concurrent.TimeUnit.SECONDS)) {
            System.out.println("线程池未结束");
        }
        System.out.println("线程池结束");
    }
}


class ThreadPollDemoTask implements Runnable {

    private final String name;

    public ThreadPollDemoTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("start task " + name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("end task " + name);
    }
}
