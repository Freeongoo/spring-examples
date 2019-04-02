package hello.entity.bidirectional.twoMain;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import org.junit.Test;

import javax.persistence.PersistenceException;
import java.util.Set;

import static java.util.Collections.singleton;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@DatabaseSetup("/author_book.xml")
public class AuthorTest extends AbstractJpaTest {

    @Test
    public void getBookFromAuthor() {
        Author author = entityManager.find(Author.class, 1L);

        Set<Book> books = author.getBooks();

        assertThat(books.size(), equalTo(2));
        assertThat(books, containsInAnyOrder(
                hasProperty("id", is(1L)),
                hasProperty("id", is(2L))
        ));
    }

    @Test(expected = IllegalStateException.class)
    public void createAuthorWithBook_WhenBookNotSaved_WhenSetToAuthor_ShouldThrowException() {
        Author author = new Author("NewAuthor");
        Book book = new Book("NewBook");

        author.setBooks(singleton(book));

        entityManager.persistAndFlush(author);
    }

    @Test(expected = IllegalStateException.class)
    public void createAuthorWithBook_WhenBookNotSaved_WhenSetToAuthorAndBook_ShouldThrowException() {
        Author author = new Author("NewAuthor");
        Book book = new Book("NewBook");

        author.setBooks(singleton(book));
        book.setAuthors(singleton(author));

        entityManager.persistAndFlush(author);
    }

    @Test
    public void createAuthorWithBook_WhenBookSaved_WhenSetToAuthor_ShouldRelationCreated() {
        Author author = new Author("NewAuthor");
        Book book = new Book("NewBook");
        entityManager.persistAndFlush(book);

        author.setBooks(singleton(book));

        Author authorFromDb = entityManager.persistFlushFind(author);
        assertThat(authorFromDb.getBooks().size(), equalTo(1));
    }

    @Test
    public void createAuthorWithBook_WhenBookSaved_WhenSetToBook_ShouldRelationNotCreated() {
        Author author = new Author("NewAuthor");
        Book book = new Book("NewBook");
        entityManager.persistAndFlush(book);

        book.setAuthors(singleton(author));

        Author authorFromDb = entityManager.persistFlushFind(author);
        assertThat(authorFromDb.getBooks().size(), equalTo(1));
    }

    @Test(expected = PersistenceException.class)
    public void createAuthorWithBook_WhenBookSaved_WhenSetToAuthorAndBook_ShouldThrowException() {
        Author author = new Author("NewAuthor");
        Book book = new Book("NewBook");
        entityManager.persistAndFlush(book);

        author.setBooks(singleton(book));
        book.setAuthors(singleton(author));

        Author authorFromDb = entityManager.persistFlushFind(author);
        assertThat(authorFromDb.getBooks().size(), equalTo(1));
    }
}