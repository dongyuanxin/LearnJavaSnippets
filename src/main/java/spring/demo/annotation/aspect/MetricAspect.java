package spring.demo.annotation.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 1、@Aspect ：被 Spring 扫描并注册为 bean 切面
 * 2、@Around ：定义切面逻辑。参数代表切面影响的范围，就是被 @MetricTime 注解修饰的方法。
 * 3、除了 @Around，还有 @Before、@After、@AfterReturning、@AfterThrowing 等
 */
@Aspect
@Component
public class MetricAspect {
    @Around("@annotation(metricTime)")
    public Object metric(ProceedingJoinPoint joinPoint, MetricTime metricTime) throws Throwable {
        String name = metricTime.value();
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long t = System.currentTimeMillis() - start;
            // 写入日志或发送至JMX:
            System.err.println("[Metrics] " + name + ": " + t + "ms");
        }
    }
}
