package stream.demo;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class OpStreamDemo {
    public static void main(String[] args) {
        // 1、map
        Stream<BigInteger> s1 = Stream.generate(new NatualSupplier());
        Stream<BigInteger> s2 = s1.map(n -> n.multiply(n));
        Stream<BigInteger> s3 = s2.map(n -> n.subtract(BigInteger.valueOf(1)))
                .limit(10);
//        System.out.println(s3);
//        s3.forEach(System.out::println);
        // 2、reduce
        System.out.println(s3.reduce(BigInteger.valueOf(0), (acc, n) -> acc.add(n)));

        // 3、Stream -> List/Queue/Map
        Stream<String> s4 = Stream.of("Apple", "", null, "Pear", "  ", "Orange");
        List<String> list4 = s4.filter(s -> s != null && s.length() > 0 && !s.isBlank()).collect(Collectors.toList());
        System.out.println(list4);

        Stream<String> s5 = Stream.of("APPL:Apple", "MSFT:Microsoft");
        Map<String, String> map5 = s5.collect(Collectors.toMap(s -> s.split(":")[0], s -> s.split(":")[1]));
        System.out.println(map5);

        // 4. Stream -> Array。需要调用构造函数引用
        List<String> list6 = List.of("Apple", "Banana", "Orange");
        String[] array6 = list6.stream().toArray(String[]::new);
        System.out.println(Arrays.stream(array6).collect(Collectors.joining(",")));

        // 5、分组输出：groupingBy 第一个参数是分组的key，第二个参数是分组的value。如果第一个参数一样，就会被分到一组
        List<String> list7 = List.of("Apple", "Banana", "Blackberry", "Coconut", "Avocado", "Cherry", "Apricots");
        Map<String, List<String>> groups7 = list7.stream()
                .collect(Collectors.groupingBy(s -> s.substring(0, 1), Collectors.toList()));
        System.out.println(groups7);

        // 6、并行操作。默认是多线程，但是线程数是CPU核数
        // boxed() 的作用是：把 LongStream（long） 转换成 Stream<Long>（Long 包装类型）
        List<Long> list = LongStream.range(1, 1000000).boxed().collect(Collectors.toList());
        long start = System.currentTimeMillis();
        Long sum = list.parallelStream().mapToLong(i -> i * 2).sum();
//        Long sum = list.stream().mapToLong(i -> i * 2).sum();
        long end = System.currentTimeMillis();
        System.out.println("parallel sum = " + sum + ", cost = " + (end - start) + "ms");

        OpStreamDemo.compareParallel();
    }

    public static void compareParallel() {
        // 生成一个大列表，用于模拟密集计算
        List<Integer> list = IntStream.range(0, 3_000_000)
                .boxed()
                .collect(Collectors.toList());

        // -------- 普通 stream --------
        long start1 = System.currentTimeMillis();
        long sum1 = list.stream()
                .mapToLong(OpStreamDemo::slowCalc)
                .sum();
        long end1 = System.currentTimeMillis();
        System.out.println("普通 stream 耗时: " + (end1 - start1) + " ms");

        // -------- parallel stream --------
        long start2 = System.currentTimeMillis();
        long sum2 = list.parallelStream()
                .mapToLong(OpStreamDemo::slowCalc)
                .sum();
        long end2 = System.currentTimeMillis();
        System.out.println("parallel stream 耗时: " + (end2 - start2) + " ms");

        System.out.println("结果一致？ " + (sum1 == sum2));
    }

    // 一个模拟 CPU 密集计算的方法（每次做一点点计算）
    private static long slowCalc(long n) {
        return n * n;
    }
}
