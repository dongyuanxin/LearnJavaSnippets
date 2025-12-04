package tomcat.demo.context;

import java.util.UUID;

/**
 * 注意下这里的设计，TomcatRequestContextHolder 实例是挂到request上的，但是真正存储上下文的变量是 static final 声明的 ThreadLocal 变量
 * 在使用时（TomContextFiler.java）里，实际上借助了“构造函数”来执行ThreadLocal的更新，借助AutoCloseable来实现自动释放
 *
 * 另一种实现：可以不实现 AutoCloseable 接口，而是直接都写成静态方法（close换成remove手动清除，构造函数换成set手动构造），这样使用起来更简单
 */
public class TomcatRequestContextHolder implements AutoCloseable {
    private static final ThreadLocal<TomcatRequestContext> ctx = new ThreadLocal<>();

    public TomcatRequestContextHolder(TomcatRequestContext ins) {
        ctx.set(ins);
    }

//    public static void set(TomcatRequestContext context) {
//        ctx.set(context);
//    }

    public static TomcatRequestContext get() {
        return ctx.get();
    }

    @Override
    public void close() throws Exception {
        TomcatRequestContextHolder.ctx.remove();
    }
}