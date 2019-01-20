package hello.service.impl;

import hello.entity.Company;
import hello.exception.ERROR_CODES;
import hello.exception.NotFoundException;
import hello.repository.CompanyRepository;
import hello.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository repository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Company get(Long id) {
        Optional<Company> company = repository.findById(id);
        if (!company.isPresent()) {
            throw new NotFoundException("Cannot find company by id: " + id, ERROR_CODES.OBJECT_NOT_FOUND);
        }
        return company.get();
    }

    @Override
    public Company get(String name) {
        Optional<Company> company = repository.findByName(name);
        if (!company.isPresent()) {
            throw new NotFoundException("Cannot find company by name: " + name, ERROR_CODES.OBJECT_NOT_FOUND);
        }
        return company.get();
    }

    @Override
    public List<Company> getAll() {
        return repository.findAll();
    }

    @Override
    public Company create(Company company) {
        return repository.save(company);
    }

    @Override
    public Company update(Long id, Company company) {
        validateExistId(id);

        company.setId(id);
        return repository.save(company);
    }

    @Override
    public void delete(Long id) {
        validateExistId(id);

        repository.deleteById(id);
    }

    private void validateExistId(Long id) {
        Optional<Company> company = repository.findById(id);
        if (!company.isPresent()) {
            throw new NotFoundException("Cannot find company by id: " + id, ERROR_CODES.OBJECT_NOT_FOUND);
        }
    }

    @Override
    public void delete(Company company) {
        repository.delete(company);
    }
}
