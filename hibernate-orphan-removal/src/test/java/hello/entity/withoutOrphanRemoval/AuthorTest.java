package hello.entity.withoutOrphanRemoval;

import hello.AbstractJpaTest;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AuthorTest extends AbstractJpaTest {

    @Test
    public void removeOneFromList() {
        Author author = new Author();
        Book book1 = new Book("123-456-7890");
        Book book2 = new Book("321-654-0987");

        author.addBook(book1);
        author.addBook(book2);

        entityManager.persist(author);
        entityManager.flush();

        author.removeBook(book1);

        flushAndClean();

        Book bookFromDb = entityManager.find(Book.class, book1.getId());
        assertThat(bookFromDb, is(notNullValue()));
        assertThat(bookFromDb.getAuthor(), nullValue()); // nothing deleted
    }

    @Test
    public void removeOneFromList_WhenRemoveOnlyFromAuthorSide_ShouldBeNotDeleted() {
        Author author = new Author();
        Book book1 = new Book("123-456-7890");
        Book book2 = new Book("321-654-0987");

        author.addBook(book1);
        author.addBook(book2);

        entityManager.persist(author);
        entityManager.flush();

        // try delete only from author side
        List<Book> books = author.getBooks();
        books.remove(book1);

        flushAndClean();

        Book bookFromDb = entityManager.find(Book.class, book1.getId());
        assertThat(bookFromDb, is(notNullValue()));
        assertThat(bookFromDb.getAuthor(), is(notNullValue())); // nothing deleted
    }

    @Test
    public void remove_WhenRemoveAllBooks_ShouldBeNotDeleted() {
        Author author = new Author();
        Book book1 = new Book("123-456-7890");
        Book book2 = new Book("321-654-0987");

        author.addBook(book1);
        author.addBook(book2);

        entityManager.persist(author);
        entityManager.flush();

        author.getBooks().clear();

        flushAndClean();

        Book bookFromDb = entityManager.find(Book.class, book1.getId());
        assertThat(bookFromDb, is(notNullValue()));
        assertThat(bookFromDb.getAuthor(), is(notNullValue())); // nothing deleted

        Author authorFromDb = entityManager.find(Author.class, author.getId());
        assertThat(authorFromDb.getBooks().size(), equalTo(2)); // nothing deleted
    }

    @Test
    public void remove_WhenWrongRemoveAllBooks_ShouldBeNotDeleted() {
        Author author = new Author();
        Book book1 = new Book("123-456-7890");
        Book book2 = new Book("321-654-0987");

        author.addBook(book1);
        author.addBook(book2);

        entityManager.persist(author);
        entityManager.flush();

        author.setBooks(null);

        flushAndClean();

        Book bookFromDb = entityManager.find(Book.class, book1.getId());
        assertThat(bookFromDb, is(notNullValue()));
        assertThat(bookFromDb.getAuthor(), is(notNullValue())); // nothing deleted

        Author authorFromDb = entityManager.find(Author.class, author.getId());
        assertThat(authorFromDb.getBooks().size(), equalTo(2)); // nothing deleted
    }
}