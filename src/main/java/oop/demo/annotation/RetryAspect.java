package oop.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Retry {
    int times() default 3;
}

public class RetryAspect {
    public static Object invoke(Object obj, Method method, Object... args) throws Exception {

        int times = 1;

        if (method.isAnnotationPresent(Retry.class)) {
            times = method.getAnnotation(Retry.class).times();
        }

        int attempt = 0;
        Exception lastEx = null;

        while (attempt < times) {
            try {
                return method.invoke(obj, args);
            } catch (Exception e) {
                lastEx = e;
                attempt++;
                System.out.println("[RetryAspect] 失败第 " + attempt + " 次，重试中...");
            }
        }

        throw lastEx;
    }
}
