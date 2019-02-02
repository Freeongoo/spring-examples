package hello.repository.dto;

import hello.entity.dto.Chair;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChairRepository extends CrudRepository<Chair, Long> {

    @Override
    List<Chair> findAll();
}
