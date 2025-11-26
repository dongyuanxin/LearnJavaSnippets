package thread.demo;

import java.util.Arrays;

public class ThreadSynchronizedDemo2 {
    static final Point7 sharedPoint7 = new Point7();

    public static void main(String[] args) throws InterruptedException {
        Thread[] ts = new Thread[] { new SonThread7(), new SonThread8(), };
        for (var t : ts) {
            t.start();
        }
        for (var t : ts) {
            t.join();
        }
    }
}

class Point7 {
    int x;
    int y;

    public void set(int x, int y) {
        long threadId = Thread.currentThread().getId();
        synchronized(this) {
            try {
                System.out.println(threadId + "-synchronized start: " + this.x + "," + this.y);
                // 获取线程i
                this.x = x;
                Thread.sleep(4000);
                this.y = y;
                System.out.println(threadId + "-synchronized end: " + this.x + "," + this.y);
            } catch (InterruptedException e) {
                System.out.println(threadId + "-error occur: " + e);
            }
        }
    }

    public int[] get() {
        // 最简单的做法
        synchronized(this) {
            int[] copy = new int[2];
            copy[0] = x;
            copy[1] = y;
            return copy;
        }

        // 错误写法：
//        int[] copy = new int[2];
//        copy[0] = x;
//        copy[1] = y;
//        return copy;
    }
}

// 推荐做法：volatile + 原子替换引用
//class Point7 {
//    private volatile int[] pair = new int[]{0, 0};
//
//    public void set(int x, int y) {
//        pair = new int[]{x, y};  // 原子替换引用
//    }
//
//    public int[] get() {
//        return pair;  // volatile 保证可见性
//    }
//}

class SonThread7 extends Thread {
    public void run() {
        ThreadSynchronizedDemo2.sharedPoint7.set(100, 200);
        System.out.println("SonThread7 get result is: " + Arrays.toString(ThreadSynchronizedDemo2.sharedPoint7.get()));
    }
}

class SonThread8 extends Thread {
    public void run() {
//        ThreadSynchronizedDemo2.sharedPoint7.set(10, 20);
        try {
            Thread.sleep(1000);
            System.out.println("SonThread8 get result is: " + Arrays.toString(ThreadSynchronizedDemo2.sharedPoint7.get()));
        } catch (InterruptedException e) {
            System.out.println("error occur: " + e);
        }
    }
}
