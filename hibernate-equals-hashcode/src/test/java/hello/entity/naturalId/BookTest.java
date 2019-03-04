package hello.entity.naturalId;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractHibernateCheckEqualsHashCodeTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

// DBUnit config:
@DatabaseSetup("/book.xml")
public class BookTest extends AbstractHibernateCheckEqualsHashCodeTest<Book> {

    @Test
    public void checkTheContentsOfTheSetUnderDifferentStatesOfTheEntity() {
        Book book = new Book("4567-5445-5434-3212");
        checkTheContentsOfTheSetUnderDifferentStatesOfTheEntity(book);
    }

    @Test
    public void transientOtherEntities_ShouldNotBeEquals() {
        Book book1 = new Book("4567-5445-5434-3212");
        Book book2 = new Book("4322-5445-5434-4323");

        assertThat(book1, is(not(equalTo(book2))));
    }

    @Test
    public void managedOtherEntities_ShouldNotBeEquals() {
        Book book1 = session.get(Book.class, 1L);
        Book book2 = session.get(Book.class, 2L);

        assertThat(book1, is(not(equalTo(book2))));
    }

    @Test
    public void managedSameEntities_ShouldBeEquals() {
        Book book1 = session.get(Book.class, 1L);
        flushAndClean();
        Book book2 = session.get(Book.class, 1L);

        assertThat(book1, (equalTo(book2)));
    }

    @Test
    public void managedToDetachSameEntity_ShouldBeEquals() {
        Book book1 = session.get(Book.class, 1L);
        em.detach(book1);

        Book book2 = session.get(Book.class, 1L);

        assertThat(book1, (equalTo(book2)));
    }

    @Test
    public void transientToManageSameEntity_ShouldNotBeEquals() {
        Book book1 = new Book("4567-5445-5434-3212");
        Book book2 = new Book("4567-5445-5434-3212");

        em.persist(book2);
        flushAndClean();

        assertThat(book1, is(not((equalTo(book2)))));
    }

    @Test
    public void managedReattachSameEntity_ShouldBeEquals() {
        Book book1 = session.get(Book.class, 1L);
        em.detach(book1);
        flushAndClean();

        Book book1AfterMerged = em.merge(book1);
        flushAndClean();

        Book book2 = session.get(Book.class, 1L);

        assertThat(book1AfterMerged, (equalTo(book2)));
    }
}