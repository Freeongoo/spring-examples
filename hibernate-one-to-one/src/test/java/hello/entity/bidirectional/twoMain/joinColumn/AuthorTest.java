package hello.entity.bidirectional.twoMain.joinColumn;

import hello.AbstractJpaTest;
import hello.entity.bidirectional.twoMain.joinColumn.Author;
import hello.entity.bidirectional.twoMain.joinColumn.Book;
import hello.sqltracker.AssertSqlCount;
import org.junit.Test;

import javax.persistence.PersistenceException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class AuthorTest extends AbstractJpaTest {

    @Test
    public void createWithoutRelation() {
        Author author = new Author("NewAuthor");
        Book book = new Book("NewBook");

        entityManager.persist(author);
        entityManager.persist(book);

        flushAndClean();

        assertThat(author.getBook(), nullValue());
        assertThat(book.getAuthor(), nullValue());
    }

    @Test
    public void createWithOneRelation() {
        Author author = new Author("NewAuthor");
        entityManager.persist(author);

        Book book = new Book("NewBook");
        book.setAuthor(author);

        entityManager.persist(book);

        flushAndClean();

        assertThat(author.getBook(), nullValue());
        assertThat(book.getAuthor(), notNullValue());
    }

    @Test
    public void createWithTwoRelation() {
        Author author = new Author("NewAuthor");
        Book book = new Book("NewBook");
        author.setBook(book);
        book.setAuthor(author);

        entityManager.persist(author);
        entityManager.persist(book);

        flushAndClean();

        assertThat(author.getBook(), notNullValue());
        assertThat(book.getAuthor(), notNullValue());

        AssertSqlCount.assertInsertCount(2);
        AssertSqlCount.assertUpdateCount(1);
    }

    @Test(expected = PersistenceException.class)
    public void delete_WithoutDeletedRelation_ShouldThrowException() {
        Author author = new Author("NewAuthor");
        Book book = new Book("NewBook");
        author.setBook(book);
        book.setAuthor(author);

        entityManager.persist(author);
        entityManager.persist(book);

        flushAndClean();

        Author authorFromDb = entityManager.find(Author.class, author.getId());
        entityManager.remove(authorFromDb);

        flushAndClean();
    }

    @Test
    public void delete_WithDeletedRelationFirst_ShouldBeOk() {
        Author author = new Author("NewAuthor");
        Book book = new Book("NewBook");
        author.setBook(book);
        book.setAuthor(author);

        entityManager.persist(author);
        entityManager.persist(book);

        flushAndClean();

        Author authorFromDb = entityManager.find(Author.class, author.getId());

        // remove relation Author from Book
        Book bookFromDb = entityManager.find(Book.class, book.getId());
        bookFromDb.setAuthor(null);
        entityManager.merge(bookFromDb);

        entityManager.remove(authorFromDb);

        flushAndClean();

        Author authorFromDb2 = entityManager.find(Author.class, author.getId());
        assertThat(authorFromDb2, equalTo(null));

        Book bookFromDb2 = entityManager.find(Book.class, book.getId());
        assertThat(bookFromDb2, notNullValue());
    }
}