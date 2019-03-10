package hello.entity;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractBaseEntity<ID> implements BaseEntity<ID> {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected ID id;

    @Override
    public ID getId() {
        return id;
    }

    @Override
    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractBaseEntity<?> that = (AbstractBaseEntity<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
