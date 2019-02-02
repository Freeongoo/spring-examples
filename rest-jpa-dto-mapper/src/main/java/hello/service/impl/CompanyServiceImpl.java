package hello.service.impl;

import hello.entity.Company;
import hello.repository.CompanyRepository;
import hello.service.AbstractService;
import hello.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl extends AbstractService<Company, Long> implements CompanyService {

    private CompanyRepository repository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    protected CrudRepository<Company, Long> getRepository() {
        return repository;
    }
}
