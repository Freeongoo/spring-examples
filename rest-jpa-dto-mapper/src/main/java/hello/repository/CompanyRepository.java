package hello.repository;

import hello.entity.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompanyRepository  extends CrudRepository<Company, Long> {

    @Override
    List<Company> findAll();
}
