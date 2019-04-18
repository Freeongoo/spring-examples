package hello.entity.bidirectional.typeOfCollection.Set;

import hello.entity.AbstractBaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "planet")
public class Planet extends AbstractBaseEntity<Long> {

    @ManyToMany
    @JoinTable(name = "planet_human",
            joinColumns = { @JoinColumn(name = "planet_id") },
            inverseJoinColumns = { @JoinColumn(name = "human_id") })
    private Set<Human> humans = new HashSet<>();

    public Set<Human> getHumans() {
        return humans;
    }

    public void setHumans(Set<Human> humans) {
        this.humans = humans;
    }
}
