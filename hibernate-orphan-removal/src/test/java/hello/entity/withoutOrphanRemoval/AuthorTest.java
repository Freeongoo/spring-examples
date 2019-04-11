package hello.entity.withoutOrphanRemoval;

import hello.AbstractJpaTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
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
        assertThat(bookFromDb.getAuthor(), nullValue());
    }
}