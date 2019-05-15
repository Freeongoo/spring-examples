package hello.entity.nPlusOneProblem.solution.joinFetch;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import hello.repository.nPlusOneProblem.solution.joinFetch.AuthorRepository;
import hello.sqltracker.AssertSqlCount;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

@DatabaseSetup({"/book_author.xml"})
public class AuthorTest extends AbstractJpaTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void problem() {
        List<Author> authors = authorRepository.findAll();
        authors.forEach(a -> a.getBooks().size());

        AssertSqlCount.assertSelectCount(4);    // so many
    }

    @Test
    public void solutionByHqlFetch() {
        List<Author> authors = getSession().createQuery(
                "select a from hello.entity.nPlusOneProblem.solution.joinFetch.Author a join fetch a.books")
                .list();

        authors.forEach(a -> a.getBooks().size());

        AssertSqlCount.assertSelectCount(1);
    }

    @Test
    public void solutionByCriteriaFetch() {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Author> query = criteriaBuilder.createQuery(Author.class);
        Root<Author> root = query.from(Author.class);

        root.fetch("books", JoinType.INNER);

        query.select(root)
                .distinct(true);

        List<Author> authors = getSession()
                .createQuery(query)
                .getResultList();

        authors.forEach(a -> a.getBooks().size());

        AssertSqlCount.assertSelectCount(1);
    }
}