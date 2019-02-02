package hello.service.jsonView.impl;

import hello.entity.jsonView.Company;
import hello.repository.jsonView.CompanyRepository;
import hello.service.AbstractService;
import hello.service.jsonView.CompanyService;
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
