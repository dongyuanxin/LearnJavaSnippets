package array.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ArraysDemo {
    public static void main(String[] args) {

        Comparator myComp = new CustomArraySortAlgorithm();
        Integer[] arr1 = { 3, 1, 2, 4, 5 };
        Integer[] sortedArr1 = Arrays.copyOf(arr1, arr1.length);
        Arrays.sort(sortedArr1, myComp); // 倒序排序
//        Arrays.sort(sortedArr1, 0, sortedArr1.length, myComp); // 可以指定排序范围
        System.out.println("soredArr1 is: " + Arrays.toString(sortedArr1)); // toString快速打印

        String[] arr2 = new String[2];
        Arrays.fill(arr2, "dyx"); // 填充
        System.out.println("arr2 is: " + Arrays.toString(arr2));

        // 比对方法：equals、hashCode都行
        System.out.println("arr1 hashCode is: " + Arrays.hashCode(arr1));
        System.out.println("arr1 equals sortedArr1: " + Arrays.equals(arr1, sortedArr1));

        // 数据检索：必须排序，二分查找
        Arrays.binarySearch(arr1, 1);

        // Array -> stream：转成stream。
        // 在需要利用 Stream 提供的强大操作能力，而数组本身很弱的场景。
        // 数组在 Java 中是非常基础的结构，功能有限（不能过滤、不能 map、不能排序扩展、不能聚合…）。而 Stream 具有高度表达力的链式操作。
        String[] arr3 = new String[]{"d", "y", "x"};
        System.out.println(Arrays.stream(arr3));
        List<String> list3 = Arrays.stream(arr3)
                .map(String::trim)
                .map(String::toUpperCase)
                .distinct() // 去重
                .toList();
//        list3.add("Z"); // 会报错java.lang.UnsupportedOperationException ，因为 list3 是一个 immutable list
        System.out.println("list3 is: " + list3.toString());

        // Array -> List
        String[] arr4 = new String[] { "测", "shi", "用例" };
        // Arrays.asList 返回的不可变数据，必须交给 ArrayList 再重新构造下
        List<String> res4 = new ArrayList<>(Arrays.asList(arr4));

        System.out.println(res4.contains("shi"));
    }
}

/**
 * 自定义排序：必须实现 Comparator 接口
 */
class CustomArraySortAlgorithm implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        return o2 - o1;
    }
}
