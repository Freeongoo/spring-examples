package hello.entity.bidirectional.typeOfCollection.Collection;

import hello.entity.AbstractBaseEntity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "glass")
public class Glass extends AbstractBaseEntity {

    @ManyToMany
    @JoinTable(name = "glass_glass_holder",
            joinColumns = { @JoinColumn(name = "glass_id") },
            inverseJoinColumns = { @JoinColumn(name = "glass_holder_id") })
    private Collection<GlassHolder> glassHolders;

    public Collection<GlassHolder> getGlassHolders() {
        return glassHolders;
    }

    public void setGlassHolders(Collection<GlassHolder> glassHolders) {
        this.glassHolders = glassHolders;
    }
}
