package com.demo.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.List;

public class JacksonDemo1 {
    public static void main(String[] args) throws Exception {
        // ObjectMapper 核心类，负责序列化和反序列化
        var mapper = new ObjectMapper();

        // java object to json string: mapper.writeValueAsString
        Person person = new Person();
        person.setAge(20);
        person.setName("张三");

        String personStr = mapper.writeValueAsString(person);
        System.out.println(personStr);

        // string to java object: mapper.readValue
        Person personParse = mapper.readValue(personStr, Person.class);
        System.out.println(personParse);
        System.out.println(personParse.getAge());

        Person person2 = new Person();
        person2.setAge(40);
        person2.setName("李四");
        List<Person> personList = List.of(person, person2);
        System.out.println("personList is" + mapper.writeValueAsString(personList));
    }
}

@Data
class Person {
    private String name;
    private Integer age;

    @JsonIgnore
    private String password;
}