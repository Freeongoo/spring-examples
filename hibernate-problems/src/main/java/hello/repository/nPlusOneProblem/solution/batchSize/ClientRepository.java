package hello.repository.nPlusOneProblem.solution.batchSize;

import hello.entity.nPlusOneProblem.solution.batchSize.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
