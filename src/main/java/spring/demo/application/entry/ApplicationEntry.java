package spring.demo.application.entry;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.demo.application.service.User;
import spring.demo.application.service.UserService;

/**
 * 1、通过 ApplicationContext 创建 Spring IoC 容器
 * 2、通过 application.xml 配置，IoC 会自动解析依赖，创建对象并且完成注入
 * 3、需要理解 Bean 的概念，另外可以直接从context上获取到对应的 Bean（类实例）
 */
@SuppressWarnings("resource")
public class ApplicationEntry {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        UserService userService = context.getBean(UserService.class);
        User user = userService.login("bob@example.com", "password");
        System.out.println(user.getName());
    }
}
