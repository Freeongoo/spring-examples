package hello.repository.jsonIdentityInfo;

import hello.entity.jsonIdentityInfo.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {

}
