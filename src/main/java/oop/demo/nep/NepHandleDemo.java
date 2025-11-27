package oop.demo.nep;

import java.util.Objects;
import java.util.Optional;

/**
 * 几种处理的NEP的方法：
 * 1、Objects.isNull 判定
 * 2、try-catch（不推荐）：不应该在运行时还遇到！
 * 3、Optional：
 *      一个可以处理NEP的容器，可以保存T也可以保存null；
 *      支持自我判定是否为空指针（isPresent）；
 *      支持orElse返回默认值；
 *      支持ofNullable（也支持of），创建null值Optional对象（正常基础类型的of都不能为null）
 */
public class NepHandleDemo {
    public static void main(String[] args) {
        Integer integer = Integer.valueOf(1);
        if (Objects.isNull(integer)) {
            System.out.println(integer + " null");
        } else {
            System.out.println(integer + " not null");
        }

        try {
            // 特别关注这里，在js中不会报错，在java中会报错，表达式右边为null。而java这里基础类型基本上也是class，所以会报错。
            Integer count = null;
            int x = count;
        } catch (NullPointerException e) {
            System.out.println("NullPointerException");
        }

        Integer count2 = null;
        int x;
        if (count2 != null) {
            x = count2;
        }

        Integer value1 = null;
        Integer value2 = Integer.valueOf(10);
        var selfIns = new NepHandleDemo();
        System.out.println("sum is " + selfIns.sum(Optional.ofNullable(value1), Optional.of(value2)));
    }

    private Integer sum(Optional<Integer> a, Optional<Integer> b) {
        // Optional.isPresent - 判断值是否存在

        System.out.println("第一个参数值存在: " + a.isPresent());
        System.out.println("第二个参数值存在: " + b.isPresent());

        // Optional.orElse - 如果值存在，返回它，否则返回默认值
        Integer value1 = a.orElse(Integer.valueOf(0));

        //Optional.get - 获取值，值需要存在
        Integer value2 = b.get();
        return value1 + value2;
    }
}
