package spring.demo.annotation.model;

import lombok.Data;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
public class Order {
    private Long id;
    private Date createdAt;

    private Long userId;
    private String productName;
    private int quantity;
    private double price;
    private OrderStatus status;

    public Order() {
    }

    public Order (Long id, Long userId, String productName, int quantity, double price, OrderStatus status) {
        this.id = id;
        this.createdAt = new Date();
        this.userId = userId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    public Order (Long userId, String productName, int quantity, double price, OrderStatus status) {
        this.id = id;
        this.createdAt = new Date();
        this.userId = userId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", userId=" + userId +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
