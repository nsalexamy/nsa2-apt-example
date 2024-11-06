package org.example.model;

import com.alexamy.nsa2.example.apt.annotations.GenerateBuilder;

@GenerateBuilder
public class User {
    private final String name;
    private final int age;

    public User() {
        this.name = "";
        this.age = 0;
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
