package hello.dto;

import java.util.Set;

/**
 * If you need to hide some field, you just need to remove this field from here.
 */
public class CompanyDto {
    private Long id;
    private String name;
    private Set<ProductTypeDto> productTypes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ProductTypeDto> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(Set<ProductTypeDto> productTypes) {
        this.productTypes = productTypes;
    }
}
