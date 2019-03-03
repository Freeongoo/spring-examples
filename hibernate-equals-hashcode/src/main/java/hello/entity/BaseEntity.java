package hello.entity;

import java.io.Serializable;

public interface BaseEntity<ID> extends Serializable {

    ID getId();

    void setId(ID id);

    String getName();

    void setName(String name);
}
