package hello.controller.annotation;

import hello.annotation.Dto;
import hello.dto.CompanyDto;
import hello.entity.Company;
import hello.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mapper-annotation/companies")
public class CompanyMapperAnnotationController {

    @Autowired
    private CompanyService service;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Dto(CompanyDto.class)
    public @ResponseBody List<Company> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Dto(CompanyDto.class)
    public @ResponseBody Company get(@PathVariable Long id) {
        return service.get(id);
    }
}
