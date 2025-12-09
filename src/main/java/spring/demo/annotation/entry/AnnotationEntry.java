package spring.demo.annotation.entry;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import spring.demo.annotation.service.AppService;
import spring.demo.annotation.service.User;
import spring.demo.annotation.service.UserService;

import java.time.ZoneId;

/**
 * 1、@Configuration：标识一个类为配置类；@ComponentScan 开启扫描，默认是当前包及其子包，这里因为设计并行关系，所以必须是指明地址，不然只能扫描 spring.demo.annotation.entry 以及子包
 * 2、@Bean: 第三方类（不是代码中自定义的类），无法使用 @Component，必须使用 @Bean
 * 3、@Qualifier：可以配合 @Bean、@Autowired 使用，指定一个名称，用于区分多个相同类型的bean
 * 4、@Profile：指定环境，只有匹配的才会被加载（-Dspring.profiles.active=test）
 */
@Configuration
@ComponentScan("spring.demo.annotation")
@PropertySource("smtp.properties")
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

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AnnotationEntry.class);
        UserService userService = context.getBean(UserService.class);
        System.out.println("userService is " + userService);
        User user = userService.login("bob@example.com", "password");
        System.out.println(user.getName());

        AppService appService = context.getBean(AppService.class);
        appService.printLogo();
    }
}
