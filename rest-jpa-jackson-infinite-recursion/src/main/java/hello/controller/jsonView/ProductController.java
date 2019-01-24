package hello.controller.jsonView;

import com.fasterxml.jackson.annotation.JsonView;
import hello.entity.jsonView.Product;
import hello.service.jsonView.ProductService;
import hello.view.ProductAndCommonViews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @JsonView({ProductAndCommonViews.class})
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Product> getAll() {
        return service.getAll();
    }

    @JsonView({ProductAndCommonViews.class})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Product get(@PathVariable Long id) {
        return service.get(id);
    }
}
