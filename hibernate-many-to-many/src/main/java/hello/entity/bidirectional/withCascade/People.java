package hello.entity.bidirectional.withCascade;

import hello.entity.AbstractBaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "people")
public class People extends AbstractBaseEntity {

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "country_people",
            joinColumns = { @JoinColumn(name = "country_id") },
            inverseJoinColumns = { @JoinColumn(name = "people_id") })
    private Set<Country> countries = new HashSet<>();

    public People() {
    }

    public People(String name) {
        this.name = name;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }
}
