package oop.demo.annotation;

import java.lang.reflect.Method;

public class AnnotationDemo {
    public static void main(String[] args) throws Exception {
        AnnotationPerson person = new AnnotationPerson("dongyuanxin");
        Method method = person.getClass().getMethod("getName");

        // 通过 Method 对象上的 invoke 来调用方法。和普通调用一样，注解无效
        System.out.println("name is " + method.invoke(person, null));
        // 通过这个切面类来执行对应的方法，才能激活注解
        LogReportAspect.invoke(person, method);


        Method method2 = person.getClass().getMethod("callApi");
        RetryAspect.invoke(person, method2);

    }
}

class AnnotationPerson {
    public String name;

    public AnnotationPerson() {
        this.name = "未命名";
    }

    public AnnotationPerson(String name) {
        this.name = name;
    }

    @LogReport("logReport注解参数") // 相当于： @LogReport(value="logReport注解参数")
    public String getName() {
        return this.name;
    }

    @Retry(times = 10)
    public String callApi() {
        if (Math.random() < 0.5) throw new RuntimeException("随机失败");
        return "success";
    }
}