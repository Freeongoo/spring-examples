package hello.controller.annotation;

import hello.annotation.Dto;
import hello.dto.ProductTypeCompanyIdDto;
import hello.entity.ProductType;
import hello.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mapper-annotation/product-types")
public class ProductTypeMapperAnnotationController {

    @Autowired
    private ProductTypeService service;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Dto(ProductTypeCompanyIdDto.class)
    public @ResponseBody List<ProductType> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Dto(ProductTypeCompanyIdDto.class)
    public @ResponseBody ProductType get(@PathVariable Long id) {
        return service.getById(id);
    }
}
