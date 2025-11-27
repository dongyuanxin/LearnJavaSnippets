package com.demo.socket.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 整体就是 Reactor 模型，也是NIO
 */
public class SocketNioTcpServerDemo {
    public static void main(String[] args) throws IOException {
        // 1. 打开 Selector
        Selector selector = Selector.open();

        // 2. 打开 ServerSocketChannel
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress("127.0.0.1", 6666));

        // 3. 必须设成非阻塞
        serverChannel.configureBlocking(false);

        // 4. 注册到 selector ，关注 ACCEPT 事件
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("NIO server started on port 6666...");

        // 5. 事件循环（单线程处理多个连接）
        while (true) {
            // 为什么监听 OP_ACCEPT 后，还需要 selector.select()？
            // 因为 Selector 是被动的，不会主动告诉你事件。
            selector.select(); // 阻塞直到有事件

            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove(); // 必须移除，否则重复处理

                // 处理 ACCEPT
                // ServerSocketChannel 才能接受连接，会触发 OP_ACCEPT 事件
                if (key.isAcceptable()) {
                    ServerSocketChannel sc = (ServerSocketChannel) key.channel();
                    SocketChannel client = sc.accept();

                    client.configureBlocking(false);
                    System.out.println("connected: " + client.getRemoteAddress());

                    // 给客户端应答
                    ByteBuffer welcome = ByteBuffer.wrap("hello this is server\n".getBytes());
                    client.write(welcome);


                    // 注册 READ 事件
                    client.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                // 处理 READ，此时key是 SocketChannel。
                // SocketChannel 不能接受连接，只能读写数据，只会触发 OP_READ（已经在上面监听了）、OP_WRITE 事件
                // 这里 buffer是 SelectionKey 的 attachment，就是上面 Buffer 的实例：
                //  1、用于当前连接的context（这个才是核心），attachment可以是任意对象（session、用户id、业务数据等等），这里用Buffer
                //  2、可以在下面创建新的ByteBuffer，但是无法解决粘包（本次read读到多个消息）和半包问题（本地read读到半份消息）
                //  3、性能更高，因为复用一个buffer
                else if (key.isReadable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer attachment = (ByteBuffer) key.attachment();

                    int n = client.read(attachment);

                    // 客户端断开
                    if (n == -1) {
                        System.out.println("client disconnected: " + client.getRemoteAddress());
                        client.close();
                        continue;
                    }

                    // 准备写回数据（echo）
                    attachment.flip();
                    client.write(attachment);
                    attachment.clear();
                }
            }
        }
    }
}
