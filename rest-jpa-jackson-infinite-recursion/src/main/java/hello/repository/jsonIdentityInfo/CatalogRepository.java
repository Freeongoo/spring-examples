package hello.repository.jsonIdentityInfo;

import hello.entity.jsonIdentityInfo.Catalog;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CatalogRepository extends CrudRepository<Catalog, Long> {

    @Override
    List<Catalog> findAll();
}
