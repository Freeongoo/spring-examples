package hello.entity.oneToMany;

public interface BaseEntity<ID> {
    ID getId();
    void setId(ID id);
    String getName();
    void setName(String name);
}
