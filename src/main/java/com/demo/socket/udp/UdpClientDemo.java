package com.demo.socket.udp;

import java.io.IOException;
import java.net.*;
import java.util.Objects;

/**
 * UDP 客户端：
 * 1、创建socket：DatagramSocket（和服务端一样）
 * 2、发送数据：DatagramSocket + byte[]
 * 3、接受数据：DatagramSocket + byte[]，获取的数据放到 DatagramPacket 中
 * 4、解码数据：byte[] -> String
 */
public class UdpClientDemo {
    public static void main(String[] args) {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            ds.setSoTimeout(1000); // 设置超时时间，如果 receive 在1秒内没有收到数据，泽报错
            ds.connect(InetAddress.getByName("localhost"), 6666);

            byte[] buf = "hello".getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            ds.send(packet);

            byte[] buffer = new byte[1024];
            packet = new DatagramPacket(buffer, buffer.length);
            ds.receive(packet);
            String resp = new String(packet.getData(), packet.getOffset(), packet.getLength());
            System.out.println("from udp server resp is: " + resp);

            ds.disconnect();
            ds.close();
        } catch (SocketException se) {
            System.out.println("se is: " + se.getMessage());
        } catch (UnknownHostException ue) {
            System.out.println("ue is: " + ue.getMessage());
        } catch (IOException ie) {
            System.out.println("ie is: " + ie.getMessage());
        }finally {
            if (Objects.nonNull(ds)) {
                ds.disconnect();
                ds.close();
            }
        }
    }
}
