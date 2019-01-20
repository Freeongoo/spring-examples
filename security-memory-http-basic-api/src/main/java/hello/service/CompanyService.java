package hello.service;

import hello.entity.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    Company get(Long id);

    Company get(String name);

    List<Company> getAll();

    Company create(Company company);

    Company update(Long id, Company company);

    void delete(Long id);

    void delete(Company company);
}
