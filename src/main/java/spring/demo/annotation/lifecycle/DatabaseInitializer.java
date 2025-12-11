package spring.demo.annotation.lifecycle;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

@Component
public class DatabaseInitializer {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS users (" //
                + "id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY, " //
                + "email VARCHAR(100) NOT NULL, " //
                + "password VARCHAR(100) NOT NULL, " //
                + "name VARCHAR(100) NOT NULL, " //
                + "UNIQUE (email))");

        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS animals (" //
                + "id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY, " //
                + "type ENUM('DOG', 'CAT', 'BIRD', 'FISH') NOT NULL, " //
                + "ownerId VARCHAR(100) DEFAULT '', " // 关联Users表
                + "name VARCHAR(100) NOT NULL, "
                + "age INT DEFAULT 0)");

        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS orders ("
                + "id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY, "
                + "userId BIGINT NOT NULL, "
                + "productName VARCHAR(100) NOT NULL, "
                + "quantity INT NOT NULL, "
                + "price DOUBLE NOT NULL, "
                + "status ENUM('PENDING', 'SHIPPED', 'DELIVERED', 'CANCELED') NOT NULL, "
                + "createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)");

    }
}
