package hello.entity;

/**
 * It is important to understand that in this solution there will be problems
 * if you need to get the type of the primary key through reflection - return type Object
 *
 * @param <ID> Primary key
 */
public interface BaseEntity<ID> {

    ID getId();

    void setId(ID id);

    String getName();

    void setName(String name);
}
