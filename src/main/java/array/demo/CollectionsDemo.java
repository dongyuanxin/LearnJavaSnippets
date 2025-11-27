package array.demo;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionsDemo {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(1, 3, 10, -1));
        Collections.reverse(list);
        System.out.println("反转后：" + list);

        Collections.shuffle(list);
        System.out.println("洗牌后：" + list);

        Collections.swap(list, 1, 2);
        System.out.println("交换后：" + list);

        Collections.sort(list);
        System.out.println("自然升序后：" + list);

        System.out.println("最大元素：" + Collections.max(list));
        System.out.println("最小元素：" + Collections.min(list));
        System.out.println("出现的次数：" + Collections.frequency(list, 1));

        System.out.println("排序后的二分查找结果：" + Collections.binarySearch(list, 1));

        Collections.fill(list, 0);
        System.out.println("填充后的结果：" + list);

//        SynchronizedList synchronizedList = Collections.synchronizedList(list);
//        不推荐，迭代器不安全，是整个对象加锁 synchronized，性能相比于 concurrent 非常低

        List<Integer> allList = new ArrayList<>();

        Collections.addAll(allList, 0, 1, 3);
        System.out.println("addAll 后：" + allList);
        System.out.println("是否没有交集：" + (Collections.disjoint(list, allList) ? "是" : "否"));

//        CollectionUtils 也是 StringUtils 之类的的工具类
        if (CollectionUtils.isEmpty(list)) {
            System.out.println("集合为空");
        }
        // CollectionUtils.union/intersection/disjunction/subtract：集合运算，并集/交集/差集/补集
    }
}
