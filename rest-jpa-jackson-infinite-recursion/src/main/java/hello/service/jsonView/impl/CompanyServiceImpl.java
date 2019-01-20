package hello.service.jsonView.impl;

import hello.entity.jsonView.Company;
import hello.exception.ERROR_CODES;
import hello.exception.NotFoundException;
import hello.repository.jsonView.CompanyRepository;
import hello.service.jsonView.CompanyService;
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
        return company
                .orElseThrow(() -> new NotFoundException("Cannot find company by id: " + id, ERROR_CODES.OBJECT_NOT_FOUND));
    }

    @Override
    public List<Company> getAll() {
        return repository.findAll();
    }
}
