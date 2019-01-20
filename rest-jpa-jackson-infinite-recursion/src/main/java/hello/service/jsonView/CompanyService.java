package hello.service.jsonView;

import hello.entity.jsonView.Company;

import java.util.List;

public interface CompanyService {

    Company get(Long id);

    List<Company> getAll();
}
