package hello.service.jsonIdentityInfo.impl;

import hello.entity.jsonIdentityInfo.Catalog;
import hello.exception.ERROR_CODES;
import hello.exception.NotFoundException;
import hello.repository.jsonIdentityInfo.CatalogRepository;
import hello.service.jsonIdentityInfo.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatalogServiceImpl implements CatalogService {

    private CatalogRepository repository;

    @Autowired
    public CatalogServiceImpl(CatalogRepository repository) {
        this.repository = repository;
    }

    @Override
    public Catalog get(Long id) {
        Optional<Catalog> catalog = repository.findById(id);
        return catalog
                .orElseThrow(() -> new NotFoundException("Cannot find catalog by id: " + id, ERROR_CODES.OBJECT_NOT_FOUND));
    }

    @Override
    public List<Catalog> getAll() {
        return repository.findAll();
    }
}
