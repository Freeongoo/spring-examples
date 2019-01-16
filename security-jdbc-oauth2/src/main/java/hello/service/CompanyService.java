package hello.service;

import hello.entity.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    Optional<Company> get(Long id);

    Optional<Company> get(String name);

    List<Company> getAll();

    void create(Company company);

    Company update(Company company);

    void delete(Long id);

    void delete(Company company);
}
