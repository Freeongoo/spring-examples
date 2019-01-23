package hello.dto;

import hello.entity.AbstractEntity;

import java.util.Set;

/**
 * Change collection type field "productTypes" to "ProductTypeDto" instead "ProductType"
 */
public class CompanyDto extends AbstractEntity {
    private Set<ProductTypeDto> productTypes; // important! Changed type collection to "ProductTypeDto" instead of "ProductType"

    public Set<ProductTypeDto> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(Set<ProductTypeDto> productTypes) {
        this.productTypes = productTypes;
    }
}
