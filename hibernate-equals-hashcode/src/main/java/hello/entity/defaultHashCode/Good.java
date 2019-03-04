package hello.entity.defaultHashCode;

import hello.entity.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Good implements BaseEntity<Long> {

    private static final long serialVersionUID = 1026762550268731861L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    private Good() {
    }

    public Good(String name) {
        this.name = name;
    }

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
        Good good = (Good) o;
        return Objects.equals(id, good.id) &&
                Objects.equals(name, good.name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Good{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
