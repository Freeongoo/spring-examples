package hello.service.jsonIdentityInfo.impl;

import hello.entity.jsonIdentityInfo.Catalog;
import hello.repository.jsonIdentityInfo.CatalogRepository;
import hello.service.AbstractService;
import hello.service.jsonIdentityInfo.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class CatalogServiceImpl extends AbstractService<Catalog, Long> implements CatalogService {

    private CatalogRepository repository;

    @Autowired
    public CatalogServiceImpl(CatalogRepository repository) {
        this.repository = repository;
    }

    @Override
    protected CrudRepository<Catalog, Long> getRepository() {
        return repository;
    }
}
