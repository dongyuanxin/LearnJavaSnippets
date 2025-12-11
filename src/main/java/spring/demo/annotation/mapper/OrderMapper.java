package spring.demo.annotation.mapper;

import org.apache.ibatis.annotations.*;
import spring.demo.annotation.model.Order;

import java.util.List;

/**
 * 1、@Mapper：标识一个类为Mapper类，Mapper类中定义的方法会映射为SQL语句。
 * 2、
 */
public interface OrderMapper {
    @Select("SELECT * FROM orders WHERE id = #{id}")
    Order findById(@Param("id") long id);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("INSERT INTO orders(userId, productName, quantity, price, status, createdAt) VALUES(#{order.userId}, #{order.productName}, #{order.quantity}, #{order.price}, #{order.status}, #{order.createdAt})")
    void insert(@Param("order") Order order);

    @Select("SELECT * FROM orders LIMIT #{offset}, #{maxResults}")
    List<Order> findAll(@Param("offset") int offset, @Param("maxResults") int maxResults);

    @Delete("DELETE FROM orders WHERE id = #{id}")
    void delete(@Param("id") long id);
}
