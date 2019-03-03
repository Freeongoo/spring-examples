package hello.entity.naturalId;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.BaseTest;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

// DBUnit config:
@DatabaseSetup("/book.xml")
public class BookTest extends BaseTest {

    @Test
    public void storeToSetBeforePersist_ShouldBeContains() {
        Set<Book> map = new HashSet<>();
        Book book1 = new Book("MyBook1", "4567-5445-5434-3212");
        Book book2 = new Book("MyBook2", "4322-5445-5434-4323");
        map.add(book1);
        map.add(book2);

        em.persist(book1);
        em.persist(book2);

        flushAndClean();

        assertTrue("The entity is not found in the Set after it's persisted.", map.contains(book1));
    }

    @Test
    public void storeToSetMerge_ShouldBeContains() {
        Set<Book> map = new HashSet<>();
        Book item = new Book("MyBook2", "4322-5445-5434-4323");
        map.add(item);

        em.persist(item);
        flushAndClean();

        Book merge1 = em.merge(item);

        assertTrue("The entity is not found in the Set after it's merged.", map.contains(merge1));
    }

    @Test
    public void transientOtherEntities_ShouldNotBeEquals() {
        Book book1 = new Book("MyBook1", "4567-5445-5434-3212");
        Book book2 = new Book("MyBook2", "4322-5445-5434-4323");

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
        Book book1 = new Book("MyBook", "4567-5445-5434-3212");
        Book book2 = new Book("MyBook", "4567-5445-5434-3212");

        em.persist(book2);

        assertThat(book1, is(not((equalTo(book2)))));
    }

    @Test
    public void managedReattachSameEntity_ShouldBeEquals() {
        Book book1 = session.get(Book.class, 1L);
        em.detach(book1);
        Book book1AfterMerged = em.merge(book1);

        Book book2 = session.get(Book.class, 1L);

        assertThat(book1AfterMerged, (equalTo(book2)));
    }
}