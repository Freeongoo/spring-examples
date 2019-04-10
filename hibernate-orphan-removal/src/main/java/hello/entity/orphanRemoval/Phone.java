package hello.entity.orphanRemoval;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "phone")
public class Phone {

    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    @Column(name = "`number`", unique = true)
    private String number;

    @ManyToOne
    private Person person;

    public Phone() {
    }

    public Phone(String number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        Phone phone = (Phone) o;
        return Objects.equals( number, phone.number );
    }

    @Override
    public int hashCode() {
        return Objects.hash( number );
    }
}
