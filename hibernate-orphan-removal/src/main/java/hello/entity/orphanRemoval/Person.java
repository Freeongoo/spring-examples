package hello.entity.orphanRemoval;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dorofeev
 */
@Entity(name = "person")
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phones = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public void addPhone(Phone phone) {
        phones.add( phone );
        phone.setPerson( this );
    }

    public void removePhone(Phone phone) {
        phones.remove( phone );
        phone.setPerson( null );
    }

}
