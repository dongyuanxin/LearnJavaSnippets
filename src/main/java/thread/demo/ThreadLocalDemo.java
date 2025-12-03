package thread.demo;

import lombok.Data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal相关类的关系：
 * ThreadLocalDemoContextHolder <- ThreadLocalDemoContext（被引用）：
 * - ThreadLocalDemoContextHolder 声明、管理和调用 ThreadLocal
 * - ThreadLocalDemoContext 具体的线程变量
 *
 * 注意：管理线程变量的类（内部用ThreadLocal引用的），一定要继承 AutoCloseable 接口，这样在任务执行结束后，会自动调用 close() 方法，清理线程变量，防止内存泄漏。
 *
 * 整体流程：
 *  启动程序（ThreadLocalDemo） ->
 *      启动子线程（ThreadLocalDemoTask）->
 *          声明线程变量（ThreadLocalDemoContext）->
 *              执行逻辑（ThreadLocalDemoInnerTask1 、ThreadLocalDemoInnerTask2
 * 在 ThreadLocalDemoInnerTask1 和 ThreadLocalDemoInnerTask2 中，会调用 ThreadLocalDemoContextHolder.get() 方法，获取当前线程的线程变量（被 TreadLocal 包裹的对象）
 */
public class ThreadLocalDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(2);
        String[] users = new String[] { "Bob", "Alice", "Tim" };
        for (String user : users) {
            es.submit(new ThreadLocalDemoTask(user));
        }

        es.awaitTermination(3, TimeUnit.SECONDS);
        es.shutdown();
    }
}

@Data
class ThreadLocalDemoContext {
    private String name;

    public ThreadLocalDemoContext(String name) {
        this.name = name;
    }
}

class ThreadLocalDemoContextHolder implements AutoCloseable {
    private static final ThreadLocal<ThreadLocalDemoContext> context = new ThreadLocal<>();

    public static void set(ThreadLocalDemoContext ctx) {
        ThreadLocalDemoContextHolder.context.set(ctx);
    }

    public static ThreadLocalDemoContext get() {
        var ins = ThreadLocalDemoContextHolder.context.get();
        return ins;
    }

    public ThreadLocalDemoContextHolder(String name) {
        ThreadLocalDemoContextHolder.context.set(new ThreadLocalDemoContext(name));
        System.out.printf("[%s] init user %s...\n", Thread.currentThread().getName(), name);
    }

    /**
     * 线程结束后，清除线程变量，防止内存泄漏带来 GC
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        System.out.printf("[%s] cleanup for user %s...\n", Thread.currentThread().getName(),
                ThreadLocalDemoContextHolder.get().getName());
        ThreadLocalDemoContextHolder.context.remove();
    }
}

class ThreadLocalDemoTask implements Runnable {
    final String username;

    public ThreadLocalDemoTask(String username) {
        this.username = username;
    }

    @Override
    public void run() {
        try (var ctx = new ThreadLocalDemoContextHolder(this.username)){
            new ThreadLocalDemoInnerTask1().process();
            new ThreadLocalDemoInnerTask2().process();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ThreadLocalDemoInnerTask1 {
    public void process() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        System.out.printf("[%s] check user %s...\n", Thread.currentThread().getName(), ThreadLocalDemoContextHolder.get().getName());
    }
}

class ThreadLocalDemoInnerTask2 {
    public void process() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        System.out.printf("[%s] %s registered ok.\n", Thread.currentThread().getName(), ThreadLocalDemoContextHolder.get().getName());
    }
}
