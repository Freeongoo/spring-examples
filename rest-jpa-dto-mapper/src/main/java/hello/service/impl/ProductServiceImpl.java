package hello.service.impl;

import hello.entity.Product;
import hello.exception.ERROR_CODES;
import hello.exception.NotFoundException;
import hello.repository.ProductRepository;
import hello.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product get(Long id) {
        Optional<Product> product = repository.findById(id);
        return product
                .orElseThrow(() -> new NotFoundException("Cannot find product by id: " + id, ERROR_CODES.OBJECT_NOT_FOUND));
    }

    @Override
    public List<Product> getAll() {
        return repository.findAll();
    }
}
