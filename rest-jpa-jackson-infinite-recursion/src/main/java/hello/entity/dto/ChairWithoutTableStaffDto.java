package hello.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"tableStaff"})
public class ChairWithoutTableStaffDto extends Chair {
}
