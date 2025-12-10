package spring.demo.annotation.service;

import java.sql.ResultSet;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import spring.demo.annotation.aspect.MetricTime;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * 1、@Component：被 Spring 扫描并注册为 bean；@Service、@Repository、@Controller 等都是 @Component 的派生注解
 * 2、@Autowired：自动注入
 * 3、@PostConstruct、@PreDestroy：IoC 生命周期
 */
@Component
public class UserService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    MailService mailService;

    @PostConstruct
    public void init() {
        System.out.println("Init mail service");
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("Shutdown mail service");
    }

//    public UserService(@Autowired MailService mailService) {
//        this.mailService = mailService;
//    }

    private List<User> users = new ArrayList<>(List.of( // users:
            new User(1, "bob@example.com", "password", "Bob"), // bob
            new User(2, "alice@example.com", "password", "Alice"), // alice
            new User(3, "tom@example.com", "password", "Tom"))); // tom

    /**
     * 请参考 ApplicationEntry 的注释第5和第6条
     * @param email
     * @param password
     * @return
     */
    @MetricTime("User.login")
    public User login(String email, String password) {
        System.out.println("UserService.login output: mailService.zoneId(AOP 错误调用姿势) is " + mailService.zoneId);
        System.out.println("UserService.login output: mailService.getZoneId(AOP 正确调用姿势) is " + mailService.getZoneId());
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                mailService.sendLoginMail(user);
                return user;
            }
        }
        throw new RuntimeException("login failed.");
    }

    public User getUser(long id) {
        return this.users.stream().filter(user -> user.getId() == id).findFirst().orElseThrow();
    }

    public User register(String email, String password, String name) {
        users.forEach((user) -> {
            if (user.getEmail().equalsIgnoreCase(email)) {
                throw new RuntimeException("email exist.");
            }
        });
        User user = new User(users.stream().mapToLong(u -> u.getId()).max().getAsLong(), email, password, name);
        users.add(user);
        mailService.sendRegistrationMail(user);
        return user;
    }

    /**
     * ******************************
     * 以下是和sql有关的实现
     * 1、queryForObject 查询单个对象/单个值，常用于count、单独对象查询
     * 2、query：高级查询，查询多个对象/结果
     * 3、执行update时，自增的key结果，可以通过 GeneratedKeyHolder 对象拿到
     * ******************************
     */

    public long getUsersInDb() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", (ResultSet rs, int rowNum) -> {
            return rs.getLong(1);
        });
    }

    public List<User> getUsersInDb(int pageIndex) {
        int limit = 2;
        int offset = limit * (pageIndex - 1);
        return jdbcTemplate.query("SELECT * FROM users LIMIT ? OFFSET ?", new BeanPropertyRowMapper<>(User.class), limit, offset);
    }

    public User registerInDb(String email, String password, String name) {
        KeyHolder holder = new GeneratedKeyHolder();
        if (1 != jdbcTemplate.update((conn) -> {
            var ps = conn.prepareStatement("INSERT INTO users(email, password, name) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, email);
            ps.setObject(2, password);
            ps.setObject(3, name);
            return ps;
        }, holder)) {
            throw new RuntimeException("Insert failed.");
        }
        System.out.printf("Insert user %s success.", holder.getKey().longValue());
        return new User(holder.getKey().longValue(), email, password, name);
    }
}
