package hello.service;

import hello.entity.Company;

import java.util.List;

public interface CompanyService extends BaseService<Company> {

    Company get(Long id);

    List<Company> getAll();
}
