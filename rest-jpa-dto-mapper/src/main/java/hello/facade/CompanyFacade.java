package hello.facade;

import hello.dto.CompanyDto;
import hello.entity.Company;
import hello.service.BaseService;
import hello.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyFacade extends AbstractFacade<CompanyDto, Company> {

    @Autowired
    private CompanyService companyService;

    @Override
    protected Class<CompanyDto> getDtoClass() {
        return CompanyDto.class;
    }

    @Override
    protected BaseService<Company> getService() {
        return companyService;
    }
}
