package com.demo.socket.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * 典型的BIO：无法多路复用，因为 readLine 会阻塞线程，一个线程就只能给一个连接
 * BIO 优化手段：线程池：线程复用，可以处理多个连接，本质上也是BIO
 *
 * ServerSocket：
 *  - accept() 方法会阻塞线程，直到有连接进来
 *  - getInputStream/getOutputStream() 获取输入输出流
 *      - 写入时需要添加换行符，因为换行符是结束符
 *      - 写入后需刷新缓冲区，不然要自然等待缓冲区刷新非常慢
 *  - getRemoteSocketAddress() 获取远程地址
 *
 *  - close() 关闭连接
 */
public class SocketBioTcpServerDemo {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(6666);
        System.out.println("server start...");
        while(true) {
            System.out.println("socket wait to accept...");
            Socket socket = ss.accept();
            System.out.println("socket accepted");

            System.out.println("connected from " + socket.getRemoteSocketAddress());
            Thread t = new SocketTcpServerDemo1Thread(socket);
            t.start();
        }
    }
}

class SocketTcpServerDemo1Thread extends Thread {
    Socket socket;

    public SocketTcpServerDemo1Thread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream input = this.socket.getInputStream()) {
            try (OutputStream output = this.socket.getOutputStream()) {
                handle(input, output);
            }
        } catch (Exception e) {
            try {
                this.socket.close();
            } catch (IOException ioe) {}
        }
        System.out.println("client disconnected");
    }

    private void handle(InputStream input, OutputStream output) throws Exception {
        var writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        writer.write("hello\n");
        writer.flush();
        for (;;) {
            String s = reader.readLine();
            if (s.equals("bye")) {
                writer.write("bye\n");
                writer.flush();
                break;
            }
            writer.write("ok: " + s + "\n");
            writer.flush();
        }
    }
}
