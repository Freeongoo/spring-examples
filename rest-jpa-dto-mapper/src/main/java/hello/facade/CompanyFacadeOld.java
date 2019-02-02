package hello.facade;

import hello.dto.CompanyDto;
import hello.entity.Company;
import hello.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Deprecated
@Service
public class CompanyFacadeOld {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ModelMapper modelMapper;

    public List<CompanyDto> getAll() {
        List<Company> companies = companyService.getAll();
        return convertToOrderDto(companies);
    }

    public CompanyDto get(Long id){
        return convertToOrderDto(companyService.getById(id));
    }

    private CompanyDto convertToOrderDto(Company company) {
        return modelMapper.map(company, CompanyDto.class);
    }

    private List<CompanyDto> convertToOrderDto(Collection<Company> companies) {
        java.lang.reflect.Type targetListType = new TypeToken<List<CompanyDto>>() {}.getType();
        return modelMapper.map(companies, targetListType);
    }
}
