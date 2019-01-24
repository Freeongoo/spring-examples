package hello.entity.jsonView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import hello.entity.AbstractEntity;
import hello.view.CompanyViews;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "company")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Company extends AbstractEntity {

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @JsonView({CompanyViews.class})
    private Set<Product> products;

    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
