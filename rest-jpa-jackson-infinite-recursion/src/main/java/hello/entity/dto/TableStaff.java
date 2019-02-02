package hello.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import hello.entity.AbstractEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "table_staff")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TableStaff extends AbstractEntity {

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "tableStaff", cascade = CascadeType.ALL)
    private Set<Chair> chairs;

    public Set<Chair> getChairs() {
        return chairs;
    }

    public void setChairs(Set<Chair> chairs) {
        this.chairs = chairs;
    }
}
