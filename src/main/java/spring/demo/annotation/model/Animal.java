package spring.demo.annotation.model;

import lombok.Data;

@Data
public class Animal {

    private long id;
    private AnimalType type;
    private String ownerId;
    private String name;
    private int age;

    public Animal() {}

    public Animal(long id, AnimalType type, String ownerId, String name, int age) {
        this.id = id;
        this.type = type;
        this.ownerId = ownerId;
        this.name = name;
        this.age = age;
    }
}
