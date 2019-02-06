package hello.repository.dto;

import hello.entity.dto.TableStaff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableStaffRepository extends JpaRepository<TableStaff, Long> {

}
