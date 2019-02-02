package hello.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import hello.entity.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "chair")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Chair extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "table_staff_id", nullable = false)
    private TableStaff tableStaff;

    public TableStaff getTableStaff() {
        return tableStaff;
    }

    public void setTableStaff(TableStaff tableStaff) {
        this.tableStaff = tableStaff;
    }
}
