package hello.service.jsonIdentityInfo;

import hello.entity.jsonIdentityInfo.Catalog;

import java.util.List;

public interface CatalogService {

    Catalog get(Long id);

    List<Catalog> getAll();
}
