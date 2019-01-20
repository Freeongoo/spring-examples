package hello.repository.jsonView;

import hello.entity.jsonView.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Override
    List<Product> findAll();
}
