package hello.repository.nPlusOneProblem.solution.joinFetch;

import hello.entity.nPlusOneProblem.solution.joinFetch.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
