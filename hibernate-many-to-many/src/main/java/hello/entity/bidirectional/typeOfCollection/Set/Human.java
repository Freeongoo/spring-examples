package hello.entity.bidirectional.typeOfCollection.Set;

import hello.entity.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "human")
public class Human extends AbstractBaseEntity {

    @ManyToMany(mappedBy = "humans")
    private Set<Planet> planets = new HashSet<>();

    public Set<Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(Set<Planet> planets) {
        this.planets = planets;
    }
}
