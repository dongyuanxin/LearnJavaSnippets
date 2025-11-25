package thread.demo;

/**
 * thread 基本用法
 *
 */
public class ThreadBaseDemo {
    public static void main(String[] args) {
        System.out.println("main start...");

        Thread t = new Thread(new SonThread());
        t.start();

        System.out.println("main end...");

//       输出取决于：线程/进程调度
//       main start会先打印外，main end打印在son run之前、son end之后或者之间，都无法确定。
//       因为从t线程开始运行以后，两个线程就开始同时运行了，并且由操作系统调度，程序本身无法确定线程的调度顺序。

//        线程优先级：Thread.setPriority(int n) // 1~10, 默认值5

//        每个线程都可以调用sleep方法，让线程暂停执行，并指定暂停的时间。但要捕获InterruptedException
//        t.start();
//        try {
//            Thread.sleep(20);
//        } catch (InterruptedException e) {}
    }
}

class SonThread implements Runnable {
    @Override
    public void run() {
        System.out.println("son start...");
        System.out.println("son end...");
    }
}
