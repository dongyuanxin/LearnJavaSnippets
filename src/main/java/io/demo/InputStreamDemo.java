package io.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * System.in: InputStream
 * System.out: PrintStream
 * 注意点：
 * 1、使用try-catch来处理System.in，会导致finally之后，均无法使用System.in，因为被关闭了。
 *    所以一般不关闭
 * 2、输入流能读取字符也可以读取字符串，读取的时候回车也会被读取到 \n
 */
public class InputStreamDemo {
    public static void main(String[] args) {
//        System.out.write('H');
//        System.out.write('i');

        //
//        System.out.println("输入字符, 按下 'q' 键退出。");
//        char c;
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
//            do {
//                c = (char) br.read();
//                System.out.write(c);
//            } while (c != 'q');
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//        }

        System.out.println("输入字符, 按下 \"end\" 键退出。");
        char[] buf = new char[1024];
        int length = 0;
        String str;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            do {
                length = br.read(buf);
                str = new String(buf, 0, length);
                System.out.write(str.getBytes(StandardCharsets.UTF_8));
            } while (length != -1 && !str.endsWith("end\n"));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Goodbye!");
    }
}
