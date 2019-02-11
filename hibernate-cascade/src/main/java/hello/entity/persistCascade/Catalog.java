package hello.entity.persistCascade;

import hello.entity.AbstractBaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "catalog")
public class Catalog extends AbstractBaseEntity<Long> {

    @OneToMany(mappedBy = "catalog", cascade = CascadeType.PERSIST)
    private List<Good> goods;

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }
}
