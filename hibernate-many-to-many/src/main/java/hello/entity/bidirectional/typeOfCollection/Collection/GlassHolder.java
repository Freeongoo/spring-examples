package hello.entity.bidirectional.typeOfCollection.Collection;

import hello.entity.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "glass_holder")
public class GlassHolder extends AbstractBaseEntity {

    @ManyToMany(mappedBy = "glassHolders")
    private Collection<Glass> glasses;

    public Collection<Glass> getGlasses() {
        return glasses;
    }

    public void setGlasses(Collection<Glass> glasses) {
        this.glasses = glasses;
    }
}
