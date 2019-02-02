package hello.controller.abstractDtoController;

import hello.controller.AbstractMapperController;
import hello.dto.CompanyDto;
import hello.entity.Company;
import hello.service.BaseService;
import hello.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mapper-abstract/companies")
public class CompanyMapperFromAbstractController extends AbstractMapperController<Company, Long, CompanyDto> {

    @Autowired
    private CompanyService service;

    @Override
    protected BaseService<Company, Long> getService() {
        return service;
    }

    @Override
    protected Class<CompanyDto> getDtoClass() {
        return CompanyDto.class;
    }
}
