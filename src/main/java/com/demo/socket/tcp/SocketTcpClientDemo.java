package com.demo.socket.tcp;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

public class SocketTcpClientDemo {
    public static void main(String[] args) throws IOException {
        Socket sock = new Socket("127.0.0.1", 6666);
        try (InputStream input = sock.getInputStream()) {
            try (OutputStream output = sock.getOutputStream()) {
                handle(input, output);
            }
        }
        sock.close();
        System.out.println("disconnected.");
    }

    private static void handle(InputStream input, OutputStream output) throws IOException {
        var writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        System.out.println("wait for server's message");
        Scanner scanner = new Scanner(System.in);
        System.out.println("[server] " + reader.readLine());
        for (;;) {
            System.out.print(">>> "); // 打印提示
            String s = scanner.nextLine(); // 读取一行输入
            writer.write(s);
            writer.newLine();
            writer.flush();
            String resp = reader.readLine();
            System.out.println("<<< " + resp);
            // BIO/NIO 客户端无法主动监听，只能通过读取空行来判断服务端是否关闭了连接
            // 或者通过心跳机制
            if (Objects.isNull(resp) || (Objects.nonNull(resp) && StringUtils.isBlank(resp))) {
                break;
            }
            if (resp.equals("bye")) {
                break;
            }
        }
    }
}
