package tdd.demo;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * import和import static 区别：
 *  import = 省略包名，导入类型
 *  import static = 省略类名，导入静态成员
 */

public class TddFactorialTest {

    /**
     * Unit提供了编写测试前准备、测试后清理的固定代码，我们称之为Fixture。
     */
    // 相当于 JUNIT5 的 BeforeAll
    @BeforeClass
    public static void callBeforeAll() {
        System.out.println("总执行一次 callBeforeClass");
    }
    // 相当于 JUNIT5 的 BeforeEach
    @Before
    public void callBefore() {
        System.out.println("每次执行前都会 callBefore");
    }

    /**
     * JUnit要求的，它会把带有@Test的方法识别为测试方法
     */
    // 常用方法：assertTrue、assertFalse、assertNotNull、assertArrayEquals()--期待结果为数组并与期望数组每个元素的值均相等
    @Test
    public void testFact() {
        assertEquals(1, TddFactorial.fact(1));
        assertEquals(2, TddFactorial.fact(2));
        assertEquals(6, TddFactorial.fact(3));
        assertEquals(3628800, TddFactorial.fact(10));
    }

    /**
     * 异常测试：注解或者assertThrows
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFactThrows() {
        TddFactorial.fact(-1);


//        函数式写法：JUit 5 和 JUit 4 都可用
//        assertThrows(IllegalArgumentException.class, () -> TddFactorial.fact(-1));

//        Executable 是 JUit 5 新增的接口，表示一个可以抛出异常的函数
//        assertThrows(IllegalArgumentException.class, new Executable() {
//            @Override
//            public void execute() throws Throwable {
//                TddFactorial.fact(-1);
//            }
//        });
    }
}
