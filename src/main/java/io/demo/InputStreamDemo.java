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
 * 2、输入流能读取字符也可以读取字符串，读取的时候回车也会被读取到'\n'
 * 3、对比单行读取和多行读取，能看来 被 BufferedReader 装饰的 System.in 这个 Stream，当遇到 \n 换行符时，能在 read() 方法中返回。
 */
public class InputStreamDemo {
    public static void main(String[] args) {
//        System.out.write('H');
//        System.out.write('i');

//        1、读取字符
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

//        2、读取单行
//        System.out.println("输入字符, 按下 \"end\" 键退出。");
//        char[] buf = new char[1024];
//        int length = 0;
//        String str;
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
//            do {
//                length = br.read(buf);
//                str = new String(buf, 0, length);
//                System.out.write(str.getBytes(StandardCharsets.UTF_8));
//            } while (length != -1 && !str.endsWith("end\n"));
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//        }

//        3、读取多行
        System.out.println("输入字符, 按下 \"end\" 键退出。");
        StringBuilder content = new StringBuilder();
        char[] buf = new char[1024];
        int length;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            do {
                length = br.read(buf);
                if (length != -1) {
                    String str = new String(buf, 0, length);
                    content.append(str);

                    // 检查是否包含结束标记
                    if (content.toString().contains("end\n")) {
                        System.out.println(content);
                        break;
                    }
                }
            } while (length != -1);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }


        System.out.println("Goodbye!");
    }
}
