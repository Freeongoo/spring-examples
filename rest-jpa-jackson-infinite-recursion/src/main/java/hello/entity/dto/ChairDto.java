package hello.entity.dto;

import hello.entity.AbstractEntity;

public class ChairDto extends AbstractEntity {

    private TableStaffWithoutChairDto tableStaff;

    public TableStaffWithoutChairDto getTableStaff() {
        return tableStaff;
    }

    public void setTableStaff(TableStaffWithoutChairDto tableStaff) {
        this.tableStaff = tableStaff;
    }
}
