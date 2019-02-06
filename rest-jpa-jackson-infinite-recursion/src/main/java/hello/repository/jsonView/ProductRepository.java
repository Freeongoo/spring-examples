package hello.repository.jsonView;

import hello.entity.jsonView.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
