package hello.entity.beanValidation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import hello.entity.AbstractBaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name="employee")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee extends AbstractBaseEntity<Long> {

    @NotNull(message = "Please provide name")
    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;

    public Employee() {}

    public Employee(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(name, employee.name) &&
                Objects.equals(role, employee.role);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
