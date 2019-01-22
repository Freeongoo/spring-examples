package hello.repository;

import hello.entity.ProductType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductTypeRepository extends CrudRepository<ProductType, Long> {

    @Override
    List<ProductType> findAll();
}
