package hello.repository.jsonView;

import hello.entity.jsonView.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompanyRepository  extends CrudRepository<Company, Long> {

    @Override
    List<Company> findAll();
}
