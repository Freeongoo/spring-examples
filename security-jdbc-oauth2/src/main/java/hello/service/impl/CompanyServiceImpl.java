package hello.service.impl;

import hello.entity.Company;
import hello.repository.CompanyRepository;
import hello.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository repository;

    public CompanyServiceImpl(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Company> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Company> get(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Company> getAll() {
        return repository.findAll();
    }

    @Override
    public void create(Company company) {
        repository.save(company);
    }

    @Override
    public Company update(Company company) {
        return repository.save(company);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(Company company) {
        repository.delete(company);
    }
}
