package hello.entity.jsonView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import hello.view.CompanyViews;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "company")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({CompanyViews.GetOne.class, CompanyViews.List.class})
    private Long id;

    @Column(nullable = false, unique = true)
    @JsonView({CompanyViews.GetOne.class, CompanyViews.List.class})
    private String name;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @JsonView({CompanyViews.GetOne.class, CompanyViews.List.class})
    private Set<Product> products;

    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
