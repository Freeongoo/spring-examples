package hello.entity.dto;

import hello.entity.AbstractEntity;

import java.util.Set;

public class TableStaffDto extends AbstractEntity {

    private Set<ChairWithoutTableStaffDto> chairs;

    public Set<ChairWithoutTableStaffDto> getChairs() {
        return chairs;
    }

    public void setChairs(Set<ChairWithoutTableStaffDto> chairs) {
        this.chairs = chairs;
    }
}
