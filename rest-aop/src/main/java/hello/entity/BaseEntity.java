package hello.entity;

/**
 * @param <ID> Primary key
 */
public interface BaseEntity<ID> {

    ID getId();

    void setId(ID id);
}
