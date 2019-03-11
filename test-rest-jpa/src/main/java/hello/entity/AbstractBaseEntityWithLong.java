package hello.entity;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractBaseEntityWithLong implements BaseEntity<Long> {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "name")
    protected String name;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractBaseEntityWithLong that = (AbstractBaseEntityWithLong) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
