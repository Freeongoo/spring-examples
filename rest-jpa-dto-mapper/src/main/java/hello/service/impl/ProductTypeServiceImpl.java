package hello.service.impl;

import hello.entity.ProductType;
import hello.exception.ERROR_CODES;
import hello.exception.NotFoundException;
import hello.repository.ProductTypeRepository;
import hello.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    private ProductTypeRepository repository;

    @Autowired
    public ProductTypeServiceImpl(ProductTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductType get(Long id) {
        Optional<ProductType> productType = repository.findById(id);
        return productType
                .orElseThrow(() -> new NotFoundException("Cannot find productType by id: " + id, ERROR_CODES.OBJECT_NOT_FOUND));
    }

    @Override
    public List<ProductType> getAll() {
        return repository.findAll();
    }
}
