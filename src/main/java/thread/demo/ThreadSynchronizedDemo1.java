package thread.demo;

public class ThreadSynchronizedDemo1 {
    public static void main(String[] args) throws InterruptedException {
        Thread[] ts = new Thread[] { new SonThread4(), new SonThread5(), new SonThread6(), new SonThread5() };
        for (var t : ts) {
            t.start();
        }
        for (var t : ts) {
            t.join();
        }
        System.out.println(ThreadSynchronizedDemo1Lock.studentCount);
        System.out.println(ThreadSynchronizedDemo1Lock.teacherCount);
    }
}

/**
 * synchronized 损耗大，必要时必须把锁的粒度缩小，尽量避免锁的粒度过大
 */
class ThreadSynchronizedDemo1Lock {
    public static final Object lock = new Object();
    public static int studentCount = 0;
    public static int teacherCount = 0;
}

class SonThread4 extends Thread {
    public void run() {
        for (int i=0; i<10000; i++) {
            synchronized(ThreadSynchronizedDemo1Lock.lock) {
                ThreadSynchronizedDemo1Lock.studentCount -= 1;
            }
        }
    }
}

class SonThread5 extends Thread {
    public void run() {
        for (int i=0; i<10000; i++) {
            synchronized(ThreadSynchronizedDemo1Lock.lock) {
                ThreadSynchronizedDemo1Lock.teacherCount += 1;
            }
        }
    }
}

class SonThread6 extends Thread {
    public void run() {
        for (int i=0; i<10000; i++) {
            synchronized(ThreadSynchronizedDemo1Lock.lock) {
                ThreadSynchronizedDemo1Lock.teacherCount -= 1;
            }
        }
    }
}