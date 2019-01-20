package hello.service.jsonView;

import hello.entity.jsonView.Product;

import java.util.List;

public interface ProductService {

    Product get(Long id);

    List<Product> getAll();
}
