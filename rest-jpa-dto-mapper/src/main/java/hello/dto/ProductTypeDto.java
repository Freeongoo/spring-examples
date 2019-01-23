package hello.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hello.entity.ProductType;

/**
 * Ignore collection: Set<Product> products;
 */
@JsonIgnoreProperties("products")
public class ProductTypeDto extends ProductType {

}
