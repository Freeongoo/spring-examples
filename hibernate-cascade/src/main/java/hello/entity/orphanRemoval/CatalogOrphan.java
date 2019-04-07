package hello.entity.orphanRemoval;

import hello.entity.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "catalog_orphan")
public class CatalogOrphan extends AbstractBaseEntity<Long> {

    @OneToMany(orphanRemoval = true)
    private List<GoodOrphan> goodOrphans;

    public List<GoodOrphan> getGoodOrphans() {
        return goodOrphans;
    }

    public void setGoodOrphans(List<GoodOrphan> goodOrphans) {
        this.goodOrphans = goodOrphans;
    }
}
