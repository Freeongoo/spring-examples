package hello.entity;

public interface BaseEntity<PK> {

    PK getId();

    void setId(PK id);
}
