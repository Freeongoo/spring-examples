package hello.repository.jsonIdentityInfo;

import hello.entity.jsonIdentityInfo.Good;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodRepository extends JpaRepository<Good, Long> {

}
