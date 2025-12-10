package spring.demo.annotation.dao;

import java.sql.Statement;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import spring.demo.annotation.model.Animal;
import spring.demo.annotation.model.AnimalType;

/**
 * 1、DAO（Data Access Object）:是数据访问层的抽象。
 * 2、整体继承自 AbstractDao， 而 AbstractDao 继承自 JdbcDaoSupport。采用的是 Template 模式，具体的实现交给子类
 */
@Component
@Transactional
public class AnimalDao extends AbstractDao<Animal> {
    public Animal createAnimal(AnimalType type, String ownerId, String name, int age) {
        KeyHolder holder = new GeneratedKeyHolder();
        System.out.println("type.name() is " + type.name());
        System.out.println("type.ordinal() is " + type.ordinal());
        System.out.println("type is " + type);
        if (1 != getJdbcTemplate().update((conn) -> {
            var ps = conn.prepareStatement("INSERT INTO animals(type, ownerId, name, age) VALUES(?,?,?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, type.name());
            ps.setObject(2, ownerId);
            ps.setObject(3, name);
            ps.setObject(4, age);
            return ps;
        }, holder)) {
            throw new RuntimeException("Insert animal failed.");
        }
        return new Animal(holder.getKey().longValue(), type, ownerId, name, age);
    }
}
