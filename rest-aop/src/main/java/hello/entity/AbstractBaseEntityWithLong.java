package hello.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * It is important to understand that when a base abstract entity is already designed,
 * it is already necessary to indicate the specific type of fields (Ex.: Long id).
 *
 * Because otherwise it will not be possible to get the type through reflection -
 * the {@link Object} will always be returned.
 */
@MappedSuperclass
public abstract class AbstractBaseEntityWithLong implements BaseEntity<Long> {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractBaseEntityWithLong that = (AbstractBaseEntityWithLong) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
