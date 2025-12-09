package spring.demo.annotation.service;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 1、@Autowired(required = false)：找不到也不报错，走默认构造函数
 */
@Component
public class MailService {
    @Autowired(required = false)
    @Qualifier("z")
    ZoneId zoneId = ZoneId.systemDefault();

    @Autowired
    private SmtpConfig smtpConfig;

    public String getTime() {
        return ZonedDateTime.now(this.zoneId).format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    public void sendLoginMail(User user) {
        System.err.println(String.format("Hi, %s! You are logged in at %s", user.getName(), getTime()));
        System.out.printf("smtpConfig.getHost is %s\n", smtpConfig.getHost());
    }

    public void sendRegistrationMail(User user) {
        System.err.println(String.format("Welcome, %s!", user.getName()));

    }
}
