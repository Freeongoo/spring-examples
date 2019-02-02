package hello.service.impl;

import hello.entity.ProductType;
import hello.repository.ProductTypeRepository;
import hello.service.AbstractService;
import hello.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeServiceImpl extends AbstractService<ProductType, Long> implements ProductTypeService {

    private ProductTypeRepository repository;

    @Autowired
    public ProductTypeServiceImpl(ProductTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    protected CrudRepository<ProductType, Long> getRepository() {
        return repository;
    }
}
