package hello.repository;

import hello.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository  extends JpaRepository<Company, Long> {

}
