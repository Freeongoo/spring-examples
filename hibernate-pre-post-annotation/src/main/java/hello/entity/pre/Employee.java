package hello.entity.pre;

import hello.entity.AbstractBaseEntity;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "employee")
public class Employee extends AbstractBaseEntity<Long> {

    @Column(name = "number")
    private Long number;

    @Column(length = 128, name = "hash_name_and_number", unique = true, nullable = false)
    private String hashNameAndNumber;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getHashNameAndNumber() {
        return hashNameAndNumber;
    }

    public void setHashNameAndNumber(String hashNameAndNumber) {
        this.hashNameAndNumber = hashNameAndNumber;
    }

    @PrePersist
    public void prePersist() {
        hashNameAndNumber = DigestUtils.md5Hex("salt" + getName() + getNumber());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Employee employee = (Employee) object;
        return Objects.equals(number, employee.number) &&
                Objects.equals(hashNameAndNumber, employee.hashNameAndNumber);
    }
}
