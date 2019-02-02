package hello.service.impl;

import hello.entity.Product;
import hello.repository.ProductRepository;
import hello.service.AbstractService;
import hello.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends AbstractService<Product, Long> implements ProductService {

    private ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }


    @Override
    protected CrudRepository<Product, Long> getRepository() {
        return repository;
    }
}
