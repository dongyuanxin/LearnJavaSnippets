package oof.demo;

import java.util.Arrays;
import java.util.Comparator;

public class JavaLambdaDemo {
    public static void main(String[] args) {
        String[] array = new String[] { "apple", "Orange", "banana", "Lemon" };
        // 请使用忽略大小写排序，并改写为Lambda表达式:
//        Arrays.sort(array, new Comparator<>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o1.compareTo(o2);
//            }
//        });
//        Arrays.sort(array, (o1, o2) -> o1.compareToIgnoreCase(o2));
        Arrays.sort(array, String::compareToIgnoreCase);
        System.out.println(String.join(", ", array));

        // lambda 函数声明（引用为 greetService1）与 定义（需要实现抽象方法）。
        GreetingService greetService1 = message -> System.out.println("Hello " + message);
        // 调用实现的抽象方法
        greetService1.sayMessage("Runoob");

        JavaLambdaDemoOtherCls.runService("dongyuanxin", msg -> System.out.println("Hi " + msg));
    }
}

/**
 * 重要要求：有且只有一个抽象方法；除此之外，可以有static/default等非具体的具备实现的方法，可以定义Objects里的public方法
 */
@FunctionalInterface
interface GreetingService
{
    /**
     * 指定一个抽象方法
     * @param message
     */
    void sayMessage(String message);
}

class JavaLambdaDemoOtherCls {
    public static void runService(String name, GreetingService greetingService) {
        greetingService.sayMessage(name);
    }
}