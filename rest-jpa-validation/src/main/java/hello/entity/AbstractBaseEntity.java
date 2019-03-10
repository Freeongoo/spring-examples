package hello.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Only with primary key
 * @param <PK>
 */
@MappedSuperclass
public abstract class AbstractBaseEntity<PK> implements BaseEntity<PK> {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected PK id;

    @Override
    public PK getId() {
        return id;
    }

    @Override
    public void setId(PK id) {
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
