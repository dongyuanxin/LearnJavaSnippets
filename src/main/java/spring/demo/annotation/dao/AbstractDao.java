package spring.demo.annotation.dao;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import jakarta.annotation.PostConstruct;

/**
 * 1、关注在构造函数中的 getParameterizedType 实现：在具体的DAO继承了当前的抽象DAO后，通过此方法可以在运行时拿到范型累心更，从而锁定对应的表名以及rowMapper
 * 2、关注在 @PostConstruct 周期内的 init 方法：在 init 方法中，将 JdbcTemplate 注入到 AbstractDao 中，并设置 AbstractDao 为 JdbcDaoSupport 的子类
 * 3、采用Template模式，实现了一些基础方法，子类（具体的DAO）不用重复实现了。
 * 4、类比JS社区的NestJS提供的 DAO，这里就是 DAO 的实现原理。注意 DTO 是 Data Transfer Object，即数据传输对象，而 DAO 则是数据访问对象，是数据处理对象。
 */
public abstract class AbstractDao<T> extends JdbcDaoSupport {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String table;
    private Class<T> entityClass;
    private RowMapper<T> rowMapper;

    public AbstractDao() {
        this.entityClass = getParameterizedType();
        this.table = this.entityClass.getSimpleName().toLowerCase() + "s";
        this.rowMapper = new BeanPropertyRowMapper<>(entityClass);
    }

    @PostConstruct
    public void init() {
        super.setJdbcTemplate(jdbcTemplate);
    }

    public T getById(long id) {
        return getJdbcTemplate().queryForObject("SELECT * FROM " + table + " WHERE id = ?", this.rowMapper, id);
    }

    public List<T> getAll(int pageIndex) {
        int limit = 100;
        int offset = limit * (pageIndex - 1);
        return getJdbcTemplate().query("SELECT * FROM " + table + " LIMIT ? OFFSET ?", this.rowMapper, new Object[] { limit, offset });
    }

    public void deleteById(long id) {
        getJdbcTemplate().update("DELETE FROM " + table + " WHERE id = ?", id);
    }

    public RowMapper<T> getRowMapper() {
        return this.rowMapper;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getParameterizedType() {
        // 获取范型父类信息
        Type type = getClass().getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Class " + getClass().getName() + " does not have parameterized type.");
        }
        // 提起第一个范型参数，检查是否属于 Class。属于则返回此类的信息
        ParameterizedType pt = (ParameterizedType) type;
        Type[] types = pt.getActualTypeArguments();
        if (types.length != 1) {
            throw new IllegalArgumentException("Class " + getClass().getName() + " has more than 1 parameterized types.");
        }
        Type r = types[0];
        if (!(r instanceof Class<?>)) {
            throw new IllegalArgumentException("Class " + getClass().getName() + " does not have parameterized type of class.");
        }
        return (Class<T>) r;
    }
}
