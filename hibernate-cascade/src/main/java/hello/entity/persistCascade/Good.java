package hello.entity.persistCascade;

import hello.entity.AbstractBaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "good")
public class Good extends AbstractBaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }
}
