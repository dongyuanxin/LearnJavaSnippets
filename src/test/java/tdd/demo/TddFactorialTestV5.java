package tdd.demo;

import org.junit.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TddFactorialTestV5 {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test()
    public void testFactThrows() {
        // 函数式写法：JUit 5 和 JUit 4 都可用
        assertThrows(IllegalArgumentException.class, () -> TddFactorial.fact(-1));

        // Executable 是 JUit 5 新增的接口，表示一个可以抛出异常的函数
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                TddFactorial.fact(-1);
            }
        });
    }

    //    参数化测试：CsvSource 注解声明多组输入输出
    @ParameterizedTest
    @CsvSource({ "abc, Abc", "APPLE, Apple", "gooD, Good" })
    public void testCapitalize(String input, String result) {
        assertEquals(result, TddFactorial.capitalize(input));
    }

    //    参数化测试：CsvFileSource 注解测试集合
    //  这里的 /capitalize.csv 文件会自动添加到 classpath 中，从 resources 目录下寻找
    @ParameterizedTest
    @CsvFileSource(resources = { "/capitalize.csv" })
    public void testCapitalize2(String input, String result) {
        assertEquals(result, TddFactorial.capitalize(input));
    }
}