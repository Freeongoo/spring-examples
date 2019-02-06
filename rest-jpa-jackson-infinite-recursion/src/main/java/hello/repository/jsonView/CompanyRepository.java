package hello.repository.jsonView;

import hello.entity.jsonView.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository  extends JpaRepository<Company, Long> {

}
