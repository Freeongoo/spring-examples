package hello.repository.dto;

import hello.entity.dto.TableStaff;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TableStaffRepository extends CrudRepository<TableStaff, Long> {

    @Override
    List<TableStaff> findAll();
}
