package hello.repository.dto;

import hello.entity.dto.Chair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChairRepository extends JpaRepository<Chair, Long> {

}
