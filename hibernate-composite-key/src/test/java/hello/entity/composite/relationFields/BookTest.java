package hello.entity.composite.relationFields;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

@DatabaseSetup({"/book_author.xml"})
public class BookTest extends AbstractJpaTest {

    @Test
    public void create_WhenAuthorInPersistentState() {
        Author author = entityManager.find(Author.class, 1L);

        BookId bookId = new BookId("123321-erwer34", author);

        Book book = new Book();
        book.setName("MyNewBook");
        book.setBookId(bookId);

        Book bookFromDb = entityManager.persistFlushFind(book);
        assertThat(bookFromDb.getBookId().getAuthor().getId(), equalTo(1L));
        assertThat(bookFromDb.getBookId().getId(), equalTo("123321-erwer34"));
    }

    @Test(expected = NullPointerException.class)
    public void create_WhenAuthorInNotPersistentState() {
        Author author = new Author();
        author.setName("myAuthor");
        BookId bookId = new BookId("123321-erwer34", author);

        Book book = new Book();
        book.setName("MyNewBook");
        book.setBookId(bookId);

        Book bookFromDb = entityManager.persistFlushFind(book);
    }
}