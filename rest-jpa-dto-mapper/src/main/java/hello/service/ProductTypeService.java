package hello.service;

import hello.entity.ProductType;

import java.util.List;

public interface ProductTypeService {

    ProductType get(Long id);

    List<ProductType> getAll();
}
