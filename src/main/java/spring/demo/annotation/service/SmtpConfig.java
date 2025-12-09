package spring.demo.annotation.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 1、已在App入口文件通过 @PropertySource("smtp.properties") 加载了配置
 * 2、配合 @Value 和 ${} 语法，能快速挂入对应的值
 */
@Component
public class SmtpConfig {
    @Value("${smtp.host:localhost}")
    private String host;

    @Value("${smtp.port:25}")
    private int port;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
