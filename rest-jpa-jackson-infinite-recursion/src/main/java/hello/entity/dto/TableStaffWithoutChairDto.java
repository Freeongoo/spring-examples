package hello.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"chairs"})
public class TableStaffWithoutChairDto extends TableStaff {
}
