package oop.demo.trywithresources;

import java.io.Closeable;
import java.io.IOException;

// try-with-resources: 都必须实现 Closeable 接口，重写 close() 方法

public class MyResourceDemo1 {
    public static void main(String[] args) {
        try (RealResource r = new RealResource()) {
            r.doSomething();
        } catch (IOException e) {
            System.out.println("error occur: " + e);
        }
        return;
    }
}

class RealResource implements Closeable {
    public RealResource() {
        System.out.println("create");
    }

    public void doSomething() {
        System.out.println("doSomething");
    }

    @Override
    public void close() throws IOException {
        System.out.println("close");
    }
}