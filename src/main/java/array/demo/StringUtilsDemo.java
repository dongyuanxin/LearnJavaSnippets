package array.demo;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtilsDemo {
    public static void main(String[] args) {
        String str1 = null;
        String str2 = "";
        String str3 = " ";
        String str4 = "abc";
        // isEmpty: 判断字符串是否为null或者长度为0
        System.out.println("=====isEmpty");
        System.out.println(StringUtils.isEmpty(str1));
        System.out.println(StringUtils.isEmpty(str2));
        System.out.println(StringUtils.isEmpty(str3));
        System.out.println(StringUtils.isEmpty(str4));
        System.out.println("=====isNotEmpty");
        System.out.println(StringUtils.isNotEmpty(str1));
        System.out.println(StringUtils.isNotEmpty(str2));
        System.out.println(StringUtils.isNotEmpty(str3));
        System.out.println(StringUtils.isNotEmpty(str4));

        // isBlank: 检测字符串是否为null或者长度为0或者由空白符(whitespace)构成
        System.out.println("=====isBlank");
        System.out.println(StringUtils.isBlank(str1));
        System.out.println(StringUtils.isBlank(str2));
        System.out.println(StringUtils.isBlank(str3));
        System.out.println(StringUtils.isBlank(str4));
        System.out.println("=====isNotBlank");
        System.out.println(StringUtils.isNotBlank(str1));
        System.out.println(StringUtils.isNotBlank(str2));
        System.out.println(StringUtils.isNotBlank(str3));
        System.out.println(StringUtils.isNotBlank(str4));

        // isNumeric: 检测字符串是否为纯数字
        System.out.println("isNumeric: " + StringUtils.isNumeric("123"));
        System.out.println("isNumeric: " + StringUtils.isNumeric("123.1"));

//        List<Integer> list2 = new ArrayList<Integer>(Arrays.asList(new Integer[]{ 1, 2, 3}));
        List<Integer> list2 = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
        System.out.println("join: " + StringUtils.join(list2, ","));

        System.out.println("contains: " + StringUtils.contains("abc", "b"));
        System.out.println("trim: " + StringUtils.trim(" abc "));
    }
}
