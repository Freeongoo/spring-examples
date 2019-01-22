package hello.service;

import hello.entity.Product;

import java.util.List;

public interface ProductService extends BaseService<Product> {

    Product get(Long id);

    List<Product> getAll();
}
