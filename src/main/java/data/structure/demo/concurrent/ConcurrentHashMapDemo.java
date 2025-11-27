package data.structure.demo.concurrent;

import java.util.Collections;
import java.util.concurrent.*;

// 帮我写一下 ConcurrentHashMapDemo 类，并且有2个子线程，操作同一个ConcurrentHashMap
public class ConcurrentHashMapDemo {
    public static void main(String[] args) throws InterruptedException{
        var map = new ConcurrentHashMap<String, String>();
        var t1 = new Thread(() -> {
            map.put("1", "1");
            map.put("2", "2");
            System.out.println("threa1 map的" + "size" + "是：" + map.size());
        });
        var t2 = new Thread(() -> {
            map.put("4", "4");
            map.put("5", "5");
            map.put("6", "6");
            System.out.println("threa2 map的" + "size" + "是：" + map.size());
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}