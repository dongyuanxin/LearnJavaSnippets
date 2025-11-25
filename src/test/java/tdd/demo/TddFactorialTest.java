package tdd.demo;
import static org.junit.Assert.*;
import org.junit.Test;

public class TddFactorialTest {

    // JUnit要求的，它会把带有@Test的方法识别为测试方法
    // 常用方法：assertTrue、assertFalse、assertNotNull、assertArrayEquals()--期待结果为数组并与期望数组每个元素的值均相等
    @Test
    public void testFact() {
        assertEquals(1, TddFactorial.fact(1));
        assertEquals(2, TddFactorial.fact(2));
        assertEquals(6, TddFactorial.fact(3));
        assertEquals(3628800, TddFactorial.fact(10));
    }

}
