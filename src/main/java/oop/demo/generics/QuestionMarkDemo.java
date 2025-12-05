package oop.demo.generics;

import java.util.List;

public class QuestionMarkDemo {
    public static void main(String[] args) {
        // 1、无界通配符（?），表示一个未知类型，可以匹配任何类型。
        List<?> list = List.of(1, 2, "hello");
        list.forEach(System.out::println);

        // 2、范围通配符-上界通配符（? extends T），表示一个类型，可以匹配 T 或者 T 的子类。
        List<? extends QuestionMarkClass1> list1 = List.of(new QuestionMarkClass1("hello"), new QuestionMarkClass2("hello", 1));
        list1.forEach(System.out::println);

        // 3、范围通配符-下界通配符（? super T），表示一个类型，可以匹配 T 或者 T 的父类。
        List<? super QuestionMarkClass2> list2 = List.of(new QuestionMarkClass1("hello"), new QuestionMarkClass2("hello", 1));
        list2.forEach(System.out::println);
    }
}

class QuestionMarkClass1 {
    private String name;

    public QuestionMarkClass1(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "QuestionMarkClass1{" +
                "name='" + name + '\'' +
                '}';
    }
}

class QuestionMarkClass2 extends QuestionMarkClass1 {
    private int age;

    public QuestionMarkClass2(String name, int age) {
        super(name);
        this.age = age;
    }

    @Override
    public String toString() {
        return "QuestionMarkClass2{" +
                "age=" + age +
                '}';
    }
}
