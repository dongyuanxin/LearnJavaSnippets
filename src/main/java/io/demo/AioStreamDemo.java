package io.demo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * AIO底层：
 * - Linux：使用 epoll + 线程池模拟异步文件 I/O（因为 Linux 不支持真正的异步文件 I/O）
 * - Windows：使用 IOCP （真正异步）
 */
public class AioStreamDemo {
    public static void main(String[] args) throws IOException, InterruptedException {
        String fileTarget = "/Users/dongyuanxin/IdeaProjects/LearnJavaSnippets/src/main/resources/readme.txt";
        Path path = Paths.get(fileTarget);

        // 1、创建异步文件通道
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        System.out.println("Start async read... (main thread not blocked)");

        // 2、异步非阻塞读取: 和同步阻塞不一样，read会理解返回。读取完成后，会自动调用线程。
        // 第一个参数：字节传入到的缓冲区，第三个参数可以被回调拿到的关联对象
        channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("Async read completed! bytes = " + result);
                attachment.flip();
                byte[] data = new byte[result];
                attachment.get(data);
                System.out.println("Content: " + new String(data));
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("Async read failed: " + exc.getMessage());
            }
        });

        System.out.println("End async read... (main thread not blocked)");
        Thread.sleep(3000); // 让异步操作有时间完成
    }
}
