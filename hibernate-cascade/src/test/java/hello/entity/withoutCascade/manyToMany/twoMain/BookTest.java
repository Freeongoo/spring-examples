package hello.entity.withoutCascade.manyToMany.twoMain;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@DatabaseSetup("/author_book.xml")
public class BookTest extends AbstractJpaTest {

    @Test
    public void getAuthorFromBook() {
        Book book = entityManager.find(Book.class, 1L);

        Set<Author> authors = book.getAuthors();

        assertThat(authors.size(), equalTo(2));
        assertThat(authors, containsInAnyOrder(
                hasProperty("id", is(1L)),
                hasProperty("id", is(2L))
        ));
    }
}