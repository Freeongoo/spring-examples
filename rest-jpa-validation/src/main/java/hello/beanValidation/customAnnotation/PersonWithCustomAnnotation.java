package hello.beanValidation.customAnnotation;

import java.util.Objects;

public class PersonWithCustomAnnotation {

    @PersonAgeConstraint
    private Integer age;

    public PersonWithCustomAnnotation(Integer age) {
        this.age = age;
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
        PersonWithCustomAnnotation that = (PersonWithCustomAnnotation) o;
        return Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PersonWithCustomAnnotation{");
        sb.append("age=").append(age);
        sb.append('}');
        return sb.toString();
    }
}
