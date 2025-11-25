package thread.demo;

public class ThreadStatusDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main start...");

        Thread t = new SonThread2();
        t.start();
        Thread.sleep(1000);
        t.interrupt(); // 主动中断子线程
        t.join(); // 等待子线程结束

        System.out.println("main end...");
    }
}

class SonThread2 extends Thread {
    @Override
    public void run() {
        System.out.println("son start...");

        Thread t = new GrandsonThread2();
        t.start();
        try {
            t.join(); // 等待子线程结束
        } catch (InterruptedException e) {
            System.out.println("son interrupted..."); // 一定会发生，因为当前线程在等待状态，孙子线程又没退出，所以当前线程没扭转状态。但是此时又被上面主线程打断了
        }
        t.interrupt(); // 中断孙子线程

        System.out.println("son end...");
    }
}

class GrandsonThread2 extends Thread {
    @Override
    public void run() {
        System.out.println("grandson start...");
        int n = 0;
        while (!isInterrupted()) { // isInterrupted: 判断当前线程是否被中断
            n++;
            System.out.println("grandson wait for interrupted: " + n);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("grandson end...");
    }
}