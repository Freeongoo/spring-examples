package hello.entity.customField;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import hello.entity.AbstractBaseEntity;
import hello.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="employee")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Employee extends AbstractBaseEntity<Long> {

    @Type(type = "json")
    @Column(name = "json", columnDefinition="TEXT")
    private Object json;

    public Employee() {}

    public Employee(String name, Object json) {
        this.name = name;
        this.json = json;
    }

    public Object getJson() {
        return json;
    }

    public void setJson(Object json) {
        this.json = json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(json, employee.json);
    }

    @Override
    public int hashCode() {
        return 31;
    }


}
