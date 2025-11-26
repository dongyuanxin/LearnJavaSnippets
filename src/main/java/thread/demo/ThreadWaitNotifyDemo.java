package thread.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


/**
 * 自定义了一个线程安全的 CustomCurrentTaskQueue 队列类，并且使用wait和notify来展示基于synchronized的多线程协作
 * 整体流程：
 *
 * 并发安全类关键设计：
 *  - 目标锁：自身this（CustomCurrentTaskQueue 实例） + synchronized 可重复锁
 *  - getTask：获取队列中的task，如果队列为空则等待。
 *      - 这里必须用 while，不然被唤醒的时候线程不会继续执行 isEmpty 检查
 *      - wait 是交出锁，进入等待状态，并且释放锁。后面必须通过 notifyAll/notify 唤醒（重新拿锁）
 *  - addTask：添加task，并唤醒所有wait等待的线程。但是具体哪个线程能拿到锁，要看os
 */
public class ThreadWaitNotifyDemo {
    public static void main(String[] args) throws InterruptedException {
        var q = new CustomCurrentTaskQueue();
        // 开启5个线程，执行task。每个task获取并且清空队列
        // 这5个线程调用了getTask方法，本质上只能通过notifyAll被唤醒，而且要去抢锁
        var ts = new ArrayList<Thread>();
        for (int i=0; i<5; i++) {
            var t = new Thread() {
                public void run() {
                    while (true) {
                        try {
                            String s = q.getTask();
                            System.out.println("execute task: " + s);
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                }
            };
            t.start();
            ts.add(t);
        }
        // 开启Add线程：不停地给队列添加task
        var add = new Thread(() -> {
            for (int i=0; i<10; i++) {
                // 放入task:
                String s = "t-" + Math.random();
                System.out.println("add task: " + s);
                q.addTask(s);
                try { Thread.sleep(100); } catch(InterruptedException e) {}
            }
        });
        add.start();
        add.join();
        Thread.sleep(100);
        // 中断前面开启的5个线程
        for (var t : ts) {
            t.interrupt();
        }
    }
}


class CustomCurrentTaskQueue {
    private Queue<String> queue = new LinkedList<>();

    public synchronized void addTask(String s) {
        this.queue.add(s);
        this.notifyAll();
    }

    public synchronized String getTask() throws InterruptedException {
        while (queue.isEmpty()) {
            this.wait(); // 不会无限死循环
        }
        return queue.remove();
    }
}