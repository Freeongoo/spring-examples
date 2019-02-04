package hello.repository.nPlusOneProblem.solution.batchSize;

import hello.entity.nPlusOneProblem.solution.batchSize.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
