package oop.demo.reflect;

public class ReflectDemo {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException {
        Class cls2Type = ReflectCls2.class;

        // 获取public字段"score": public int oop.demo.reflect.ReflectCls2.score
        System.out.println(cls2Type.getField("score"));
        // 获取继承的public字段"name": public java.lang.String oop.demo.reflect.ReflectCls1.name
        System.out.println(cls2Type.getField("name"));
        // 获取private字段"grade": private int oop.demo.reflect.ReflectCls2.grade
        System.out.println(cls2Type.getDeclaredField("grade"));
    }
}

class ReflectCls2 extends ReflectCls1 {
    public int score;
    private int grade;
}

class ReflectCls1 {
    public String name;
}