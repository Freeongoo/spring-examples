package hello.service.jsonView.impl;

import hello.entity.jsonView.Product;
import hello.repository.jsonView.ProductRepository;
import hello.service.AbstractService;
import hello.service.jsonView.ProductService;
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
