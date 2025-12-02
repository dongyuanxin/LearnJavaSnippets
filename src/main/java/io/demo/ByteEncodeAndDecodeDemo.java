package io.demo;

import java.io.*;

public class ByteEncodeAndDecodeDemo {
    /**
     * 对象序列化：
     * 1、一个Java对象要能序列化，必须实现一个特殊的java.io.Serializable接口
     * 2、Java对象序列化允许定义特殊的 serialVersionUID 静态变量，标识该对象序列化的版本号，如果对象序列化的版本号与反序列化的版本号不一致，则抛出异常
     * 3、对象序列化和反序列化必须借助 ObjectOutputStream 和 ObjectInputStream
     */
    public static void main(String[] args) {
        String fileTarget = "/Users/dongyuanxin/IdeaProjects/LearnJavaSnippets/src/main/resources/person.bin";
        try {
            IoStreamDemo.createFileIfNotExists(fileTarget);
            encodePerson(fileTarget, "dongyuanxin", 18);
            decodePerson(fileTarget);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void encodePerson(String path, String name, int age) throws FileNotFoundException, IOException {
        PersonSerializable person = new PersonSerializable(name, age);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(person); // 还有writeInt、writeBoolean、writeChar、writeFloat、writeDouble、writeLong、writeUTF等方法
            System.out.println("序列化成功");
        }
    }

    public static void decodePerson(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            PersonSerializable person = (PersonSerializable) ois.readObject(); // 还有 readInt、readBoolean、readChar、readFloat、readDouble、readLong、readUTF等方法
            System.out.println("反序列化得到对象：" + person);
        }
    }

}

/**
 * 1、一个Java对象要能序列化，必须实现一个特殊的java.io.Serializable接口
 * 2、Java对象序列化允许定义特殊的 serialVersionUID 静态变量，标识该对象序列化的版本号，如果对象序列化的版本号与反序列化的版本号不一致，则抛出异常
 */
class PersonSerializable implements Serializable {
    private static final long serialVersionUID = 1L;


    String name;
    int age;

    public PersonSerializable(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "PersonToEncode{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
