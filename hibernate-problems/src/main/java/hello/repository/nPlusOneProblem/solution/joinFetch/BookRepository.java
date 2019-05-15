package hello.repository.nPlusOneProblem.solution.joinFetch;

import hello.entity.nPlusOneProblem.solution.joinFetch.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
