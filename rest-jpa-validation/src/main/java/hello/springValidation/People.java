package hello.springValidation;

import java.util.Objects;

public class People {
    private int age;

    public People(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        People people = (People) o;
        return age == people.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(age);
    }
}
