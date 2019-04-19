package hello.entity.bidirectional.withCascade;

import hello.entity.AbstractBaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "country")
public class Country extends AbstractBaseEntity {

    @ManyToMany(
            cascade = {CascadeType.ALL},
            mappedBy = "countries")
    private Set<People> peoples = new HashSet<>();

    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }

    public Set<People> getPeoples() {
        return peoples;
    }

    public void setPeoples(Set<People> peoples) {
        this.peoples = peoples;
    }
}
