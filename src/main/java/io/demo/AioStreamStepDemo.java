package io.demo;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * 进阶用例：
 *  分片异步非阻塞读取
 *  基于channel，每次读取指定大小的文件
 */
public class AioStreamStepDemo {
    public static void main(String[] args) throws IOException , InterruptedException {
        String fileTarget = "/Users/dongyuanxin/IdeaProjects/LearnJavaSnippets/src/main/resources/readme.txt";

        Path path = Path.of(fileTarget);

        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(
                path, StandardOpenOption.READ)) {

            long fileSize = channel.size();
            System.out.println("File size = " + fileSize + " bytes");

            ByteBuffer buffer = ByteBuffer.allocate(2); // 每次读取 2 字节

            StringBuilder result = new StringBuilder();

            readNext(channel, buffer, 0, fileSize, result);

            // 阻塞主线程等待（真实项目可用 CountDownLatch）
            Thread.sleep(5000);
        }
    }

    static void readNext(AsynchronousFileChannel channel, ByteBuffer buffer, long position, long fileSize, StringBuilder result) {
        if (position >= fileSize) {
            System.out.println("\n===== 完整结果 =====");
            System.out.println(result.toString());
            return;
        }

        buffer.clear();

        channel.read(buffer, position, null, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer bytesRead, Object attachment) {
                if (bytesRead == -1) {
                    // 文件结束
                    System.out.println("\n===== 完整结果 =====");
                    System.out.println(result.toString());
                    return;
                }

                buffer.flip();
                byte[] data = new byte[bytesRead];
                buffer.get(data);

                result.append(new String(data));

                double percent = (double) (position + bytesRead) * 100 / fileSize;
                System.out.printf("读取位置: %d, 读取字节: %d, 进度: %.2f%%%n",
                        position, bytesRead, percent);

                // 继续读取下一段
                readNext(channel, buffer, position + bytesRead, fileSize, result);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.err.println("读取失败: " + exc.getMessage());
            }
        });
    }
}
