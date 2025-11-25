package thread.demo;

public class ThreadVolatileDemo {
    public static void main(String[] args)  throws InterruptedException {
        SonThread3 t = new SonThread3();
        t.start();
        Thread.sleep(100);
        t.running = false; // 标志位置为false
    }
}

class SonThread3 extends Thread {
    public volatile boolean running = true;
    public void run() {
        int n = 0;
        while (running) {
            n ++;
            System.out.println(n + " hello!");
        }
        System.out.println("end!");
    }
}