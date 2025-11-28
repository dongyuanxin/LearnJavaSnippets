package com.demo.socket.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * 1、udp 面向数据包面向消息，而不是像 tcp 那样面向连接，写法上简单很多
 * 2、packet包的结构是：packet1: address=1.1.1.1, port=9000, data="A" 。所以即使不是面向连接，在ds调用send时，依然知道发送方地址和端口，所以不需要建立连接。
 * 3、如果消息体超过了缓冲区大小，那么就会被截断剩余丢弃，因为UDP协议就不是可靠的，实现上本身就不是流，所以不能保证消息的顺序，不能保证消息不丢失。
 * 4、上面的解决方法：扩大缓冲区、自己设计分片消息协议每次只传一小部分、换用tcp/quic（大数据传输 顺序保证 可靠性 抗丢包）
 */
public class UdpServerDemo {
    public static void main(String[] args) throws Exception {
        DatagramSocket ds = new DatagramSocket(6666); // 监听指定端口

        for (;;) {
            // ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 数据缓冲区: 不使用 ByteBuffer，因为 UDP 没有缓冲区概念，而且接口实现也比较老了
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            // 内核把消息按顺序放入 UDP 队列，Java receive() 逐个取出
            // ds收取一个UDP数据包，放到 packet 中，会阻塞线程
            System.out.println("waiting for data...");
            ds.receive(packet);

            // 收取到的数据存储在buffer中，由packet.getOffset(), packet.getLength()指定起始位置和长度
            // 将其按UTF-8编码转换为String:
            String s = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
            System.out.println("received data: " + s);

            // 发送数据:
            byte[] data = "ACK".getBytes(StandardCharsets.UTF_8);
            packet.setData(data);
            ds.send(packet);
        }
    }
}
