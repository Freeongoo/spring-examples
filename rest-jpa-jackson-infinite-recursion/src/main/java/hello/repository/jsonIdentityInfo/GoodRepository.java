package hello.repository.jsonIdentityInfo;

import hello.entity.jsonIdentityInfo.Good;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GoodRepository extends CrudRepository<Good, Long> {

    @Override
    List<Good> findAll();
}
