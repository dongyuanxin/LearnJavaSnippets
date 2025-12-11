package spring.demo.annotation.entry;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import spring.demo.annotation.model.AnimalType;
import spring.demo.annotation.model.OrderStatus;
import spring.demo.annotation.service.AnimalService;
import spring.demo.annotation.service.AppService;
import spring.demo.annotation.model.User;
import spring.demo.annotation.service.OrderService;
import spring.demo.annotation.service.UserService;

import javax.sql.DataSource;
import java.time.ZoneId;
import java.util.List;

/**
 * 1、@Configuration：标识一个类为配置类；@ComponentScan 开启扫描，默认是当前包及其子包，这里因为设计并行关系，所以必须是指明地址，不然只能扫描 spring.demo.annotation.entry 以及子包
 * 2、@Bean: 第三方类（不是代码中自定义的类），无法使用 @Component，必须使用 @Bean
 * 3、@Qualifier：可以配合 @Bean、@Autowired 使用，指定一个名称，用于区分多个相同类型的bean
 * 4、@Profile：指定环境，只有匹配的才会被加载（-Dspring.profiles.active=test）
 * 5、@EnableAspectJAutoProxy：开启 AspectJ （本质上是代理）AOP 编程。开启后，底层使用 CGLIB 来实现运行期动态创建Proxy
 * 6、Spring 的AOP 基于 CGLIB 实现的 Proxy，本质上是将使用AOP注解的类，全部在运行时换成 Proxy Class。
 *  有一说一，这里的代理类实现时，只实现了方法的代理，没有实现属性的代理，所以直接去访问属性时，指向是空的。
 *  至于为什么没有在继承后的构造函数，按理说应该会初始化原始类的属性，为什么没有呢？因为继承后的构造函数并没有调用super()。
 *  再看 UserService.login() 中使用 MailService，实际上运行时访问的是代理类，自然读不到 zoneId ，但是可以调用 getZoneId
 *  7、通过 JbdcTemplate 和 DataSource 这两个Bean来完成数据库操作。
 *  8、通过 @EnableTransactionManagement 来开启事务注解，需要再给IoC注入一个PlatformTransactionManager事务管理器；
 *     通过 @Transactional 来开启事务，注意事务本身是通过ThreadLocal实现的；
 *     另外事务的传播默认是 REQUIRED，也就是默认是开启事务的。
 *  9、@MapperScan 半自动 ORM 框架 MyBatis 会将 java class（mapper）生成为 SQL 语句，需要提前创建和注入 SqlSessionFactoryBean
 *    ORM的流程：Controller（代码里没有） -> Service -> Mapper -> ORM Framework(MyBatis) -> SQL
 */
@Configuration
@ComponentScan("spring.demo.annotation")
@PropertySource("smtp.properties")
@PropertySource("jdbc.properties")
@MapperScan("spring.demo.annotation.mapper")
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class AnnotationEntry {
    @Bean
    @Qualifier("z")
    @Profile("!test")
    ZoneId createZoneId() {
        return ZoneId.of("Z");
    }

    @Bean
    @Qualifier("z")
    @Profile("test")
    ZoneId createZoneIdForTest() {
        return ZoneId.of("America/New_York");
    }

    @Bean
    @Qualifier("utc8")
    ZoneId createZoneOfUTC8() {
        return ZoneId.of("UTC+08:00");
    }

    @Value("${jdbc.url}")
    String jdbcUrl;

    @Value("${jdbc.username}")
    String jdbcUsername;

    @Value("${jdbc.password}")
    String jdbcPassword;

    @Bean
    DataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(jdbcUsername);
        config.setPassword(jdbcPassword);
        config.addDataSourceProperty("autoCommit", "true");
        config.addDataSourceProperty("connectionTimeout", "5");
        config.addDataSourceProperty("idleTimeout", "60");
        return new HikariDataSource(config);
    }

    @Bean
    JdbcTemplate createJbdcTemplate(@Autowired DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    SqlSessionFactoryBean createSqlSessionFactory(@Autowired DataSource dataSource) {
        try {
            var sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);
            return sqlSessionFactoryBean;
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Bean
    PlatformTransactionManager createTxManager(@Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AnnotationEntry.class);
        UserService userService = context.getBean(UserService.class);
        System.out.println("userService is " + userService);
        User user = userService.login("bob@example.com", "password");
        System.out.println(user.getName());

        AppService appService = context.getBean(AppService.class);
        appService.printLogo();

        /**
         * 以下是体现DB的用法
         */
//        userService.registerInDb("bob@example.com", "password1", "Bob");
//        userService.registerInDb("alice@example.com", "password2", "Alice");
//
//        List<User> users = userService.getUsersInDb(1);
//        // print users
//        users.stream()
//                .map(item -> "打印批量用户查询结果: " + item)
//                .forEach(System.out::println);


        /**
         * 以下体现DAO的用法
         */
//        AnimalService animalService = context.getBean(AnimalService.class);
//        animalService.createAnimal(AnimalType.DOG, "1", "Dog", 1);

        /**
         * 以下体现半自动ORM框架 MyBatis 的用法
         */
        OrderService orderService = context.getBean(OrderService.class);
        orderService.insert(1L, "Product1", 1, 10.00, OrderStatus.PENDING);
        orderService.getOrderList(0, 10)
                .stream()
                .map(item -> "打印批量订单查询结果: " + item)
                .forEach(System.out::println);
    }
}
