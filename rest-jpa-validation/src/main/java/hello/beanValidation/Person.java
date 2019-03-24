package hello.beanValidation;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Person {

    // validate with default message
    @Size(min = 2, max = 50)
    private String name;

    // validate with custom message with passed value ('${validatedValue}')
    @Size(min = 2, max = 50, message = "Param value: '${validatedValue}' size must be between {min} and {max}")
    private String secondName;

    @Digits(integer = 3, fraction = 0, message = "Not more 3 chars")
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Person(String name, String secondName, Integer age) {
        this(name, age);
        this.secondName = secondName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(secondName, person.secondName) &&
                Objects.equals(age, person.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, secondName, age);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("name='").append(name).append('\'');
        sb.append(", secondName='").append(secondName).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }
}
