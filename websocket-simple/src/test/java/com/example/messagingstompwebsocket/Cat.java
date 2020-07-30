package com.example.messagingstompwebsocket;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author dorofeev
 */
public class Cat implements Serializable {

    private static final long serialVersionUID = 3740644480458055689L;

    private int age;
    private String name;

    public Cat() {
    }

    public Cat(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Cat cat = (Cat) object;
        return age == cat.age &&
                Objects.equals(name, cat.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cat{");
        sb.append("age=").append(age);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
