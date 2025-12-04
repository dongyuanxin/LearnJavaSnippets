package oop.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * java 的注解分2步走：
 * 1、通过 @interface 先定义注解以及注解的属性（默认字段为value，也可以是自定义字段）
 * 2、再定义一个切面类，这个切面类里获取注解属性，并执行方法
 * 3、外界是通过这个切面类来执行对应的方法，才能激活注解（如果直接执行的话，注解无效）
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface LogReport {
    String value() default "";
    String time() default "";
}

public class LogReportAspect {
    public static void invoke(Object obj, Method method, Object... args) throws Exception {
        if (method.isAnnotationPresent(LogReport.class)) {
            LogReport log = method.getAnnotation(LogReport.class);
            System.out.println("[LogReportAspect] @LogReport's params are "+ "value(" + log.value() + ") and time(" + log.time() + ")");
        }

        Object result = method.invoke(obj, args);
        System.out.println("[LogReportAspect] " + obj.getClass().getName() + "." + method.getName() + "(" + args + ")" + " return " + result);
    }
}
