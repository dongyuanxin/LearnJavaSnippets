package stream.demo;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class CreateStreamDemo {
    public static void main(String[] args) {
        /**
         * 1、自定义流
         */
        Stream<BigInteger> naturals = Stream.generate(new NatualSupplier());
        naturals.limit(20).forEach(System.out::println);

        /**
         * 2、数组或者collection生成
         */
        // 对于数组，调用 Arrays.stream() 方法
        Stream<String> stream2 = Arrays.stream(new String[]{"hello", "world"});
        stream2.forEach(System.out::println);
        // 对于Collection（List、Set、Queue等），直接调用stream()方法就可以获得Stream
        Stream<String> stream3 = List.of("A", "B", "C").stream();
        stream3.forEach(System.out::println);

        /**
         * 3、Stream.of 直接创建
         */
        var stream4 = Stream.of("直接", "创建");
        stream4.forEach(System.out::println);

        /**
         * 4、从文件中生成流: pass
         */

        /**
         * 5、Java的范型不支持基础类型，因此无法 Stream<int> 这样类型会发生compile error。
         * Java标准库提供了IntStream、LongStream和DoubleStream这三种使用基本类型的Stream，它们的使用方法和泛型Stream没有大的区别，设计这三个Stream的目的是提高运行效率
         */
        // 这里的 var 是 IntStream
        var stream5 = Arrays.stream(new int[] {1, 2, 3});
        stream5.forEach(System.out::println);
        LongStream stream6 = List.of("1", "2").stream().mapToLong(Long::parseLong);
        stream6.forEach(System.out::println);
    }

}

/**
 * 自定义 Stream 生成器：
 * 1、实现 Supplier 接口
 * 2、配合 Stream.generate 使用
 * 3、流是惰性计算，不占空间
 */
class NatualSupplier implements Supplier<BigInteger> {
    BigInteger n = BigInteger.ZERO;

    @Override
    public BigInteger get() {
        n = n.add(BigInteger.ONE);
        return n;
    }
}