package spring.demo.annotation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.demo.annotation.mapper.OrderMapper;
import spring.demo.annotation.model.Order;
import spring.demo.annotation.model.OrderStatus;

import java.util.List;

@Component
@Transactional
public class OrderService {
    @Autowired
    OrderMapper orderMapper;

    public Order findById(long id) {
        return orderMapper.findById(id);
    }

    public List<Order> getOrderList(int offset, int maxResults) {
        return orderMapper.findAll(offset, maxResults);
    }

    public void insert(Long userId, String productName, int quantity, double price, OrderStatus status) {
        var order = new Order(userId, productName, quantity, price, status);
        orderMapper.insert(order);
    }
}
