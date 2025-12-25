package oof.demo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OofBadCase {
    public static void main(String[] args) {
        // 1、复杂对象会原位修改，存在风险
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.stream().forEach(n -> n++);
        numbers.stream().forEach(System.out::println);

        // 2、并发流操作的时候，底层数据结构必须是并发安全的，否则会抛异常 ConcurrentModificationException
        List<Integer> numbers2 = List.of(1, 2, 3);
        // 更安全写法：List<Integer> list3 = new CopyOnWriteArrayList<>(List.of(1, 2, 3));
        numbers2.parallelStream().reduce((a, b) -> a + b);

        // 3、复杂链式调用会导致中间对象过多（每一步都是一个stream），核心还是影响了JVM中的JIT的优化（能在运行时将高频字节码一次性转成机器码）
        // 这个和nodejs不一样，js本身就是function作为一级对象。性能优化比较好做。

        var users = List.of(
                new OofBadCaseUser(1L, "Alice"),
                new OofBadCaseUser(2L, "Bob"),
                new OofBadCaseUser(1L, "Charlie") // id = 1 重复
        );

        // 4、另外stream提供的操作符api，也存在很多坑。比如Map必须处理key重复问题，不然崩
        Map<Long, OofBadCaseUser> userMap = users.stream()
                .collect(Collectors.toMap(
                        OofBadCaseUser::getId,
                        user -> user,
                        // 不加的话，就会崩
                        (user1, user2) -> user1.getId().equals(user2.getId()) ? user1 : user2
                ));
    }
}

@Data
@AllArgsConstructor
class OofBadCaseUser {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "'}";
    }
}