package hello.entity.orphanRemoval;

import hello.entity.AbstractBaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "good_orphan")
public class GoodOrphan extends AbstractBaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_orphan_id")
    private CatalogOrphan catalogOrphan;

    public CatalogOrphan getCatalogOrphan() {
        return catalogOrphan;
    }

    public void setCatalogOrphan(CatalogOrphan catalogOrphan) {
        this.catalogOrphan = catalogOrphan;
    }
}
