package io.demo;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 1、write/read 都是阻塞的
 * 2、OutputStream 是超类
 *  FileOutputStream 实现了文件流输出
 *  ByteArrayOutputStream 在内存中模拟一个字节流输出
 * 3、InputStream 是超类
 */
public class IoStreamDemo {
    public static void main(String[] args) throws IOException {
        String fileTarget = "/Users/dongyuanxin/IdeaProjects/LearnJavaSnippets/src/main/resources/readme.txt";
        createFileIfNotExists(fileTarget);

        OutputStream output = new FileOutputStream(fileTarget);
        output.write("hello world!".getBytes(StandardCharsets.UTF_8));
        output.flush(); // 强刷 jvm 的写入缓冲区
        output.close();

        InputStream input = new FileInputStream(fileTarget);
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[1024]; // 缓冲区大小为 1024 个字节
        int n;
        while((n = input.read(buffer)) != -1) {
            sb.append(new String(buffer, 0, n, StandardCharsets.UTF_8));
            System.out.println("read " + n + " bytes.");
        }
        System.out.println(sb);
        input.close();
    }

    public static void createFileIfNotExists(String path) throws IOException {
        try {
            File file = new File(path);
            if(!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println("创建文件成功：" + path);
                } else {
                    System.out.println("创建文件失败：" + path);
                }
            }
        } catch (IOException e) {
            throw new IOException("创建文件出错：" + path);
        }
    }
}
