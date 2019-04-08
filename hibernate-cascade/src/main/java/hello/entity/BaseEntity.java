package hello.entity;

/**
 * not the best idea to do generic <ID> for primary key
 * since when working with an entity through reflection, it will receive a type as an {@link Object}
 *
 * @param <ID> primary key
 */
public interface BaseEntity<ID> {

    ID getId();

    void setId(ID id);

    String getName();

    void setName(String name);
}
