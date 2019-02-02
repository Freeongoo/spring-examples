package hello.entity.oneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@Entity
@Table(name = "post")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post extends AbstractBaseEntity<Long> {

    public Post() {
    }

    public Post(String name) {
        this.name = name;
    }
}
