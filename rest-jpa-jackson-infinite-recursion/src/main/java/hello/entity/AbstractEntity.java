package hello.entity;

import com.fasterxml.jackson.annotation.JsonView;
import hello.view.CommonViews;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractEntity implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({CommonViews.class})
    protected Long id;

    @Column(nullable = false, unique = true)
    @JsonView({CommonViews.class})
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
}
