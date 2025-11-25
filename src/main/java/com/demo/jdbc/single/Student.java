package com.demo.jdbc.single;

import lombok.Data;

@Data
public class Student {
    private long id;
    private String name;
    private boolean gender;
    private int grade;
    private int score;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", grade=" + grade +
                ", score=" + score +
                '}';
    }
}
