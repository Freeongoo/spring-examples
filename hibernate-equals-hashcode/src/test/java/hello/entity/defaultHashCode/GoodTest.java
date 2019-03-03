package hello.entity.defaultHashCode;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.BaseTest;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

// DBUnit config:
@DatabaseSetup("/good.xml")
public class GoodTest extends BaseTest {

    @Test
    public void storeToSetBeforePersist_ShouldBeContains() {
        Set<Good> map = new HashSet<>();
        Good good1 = new Good("John");
        Good good2 = new Good("Mike");
        map.add(good1);
        map.add(good2);

        em.persist(good1);
        em.persist(good2);

        assertTrue(map.contains(good1));
    }

    /*@Test
    public void storeToSetBeforeMerge_ShouldBeContains() {
        Set<Good> map = new HashSet<>();
        Good good1 = new Good("John");
        Good good2 = new Good("Mike");
        map.add(good1);
        map.add(good2);

        Good merge1 = em.merge(good1);
        Good merge2 = em.merge(good2);

        assertTrue(map.contains(merge1));
        assertTrue(map.contains(merge2));
    }*/

    @Test
    public void transientOtherEntities_ShouldNotBeEquals() {
        Good good1 = new Good("John");
        Good good2 = new Good("Mike");

        assertThat(good1, is(not(equalTo(good2))));
    }

    @Test
    public void managedOtherEntities_ShouldNotBeEquals() {
        Good good1 = session.get(Good.class, 1L);
        Good good2 = session.get(Good.class, 2L);

        assertThat(good1, is(not(equalTo(good2))));
    }

    @Test
    public void managedSameEntities_ShouldBeEquals() {
        Good good1 = session.get(Good.class, 1L);
        Good good2 = session.get(Good.class, 1L);

        assertThat(good1, (equalTo(good2)));
    }

    @Test
    public void managedToDetachSameEntity_ShouldBeEquals() {
        Good good1 = session.get(Good.class, 1L);
        em.detach(good1);

        Good good2 = session.get(Good.class, 1L);

        assertThat(good1, (equalTo(good2)));
    }

    @Test
    public void transientToManageSameEntity_ShouldNotBeEquals() {
        Good good1 = new Good("John");
        Good good2 = new Good("John");

        em.persist(good2);

        assertThat(good1, is(not((equalTo(good2)))));
    }

    @Test
    public void managedReattachSameEntity_ShouldBeEquals() {
        Good good1 = session.get(Good.class, 1L);
        em.detach(good1);
        Good good1AfterMerged = em.merge(good1);

        Good good2 = session.get(Good.class, 1L);

        assertThat(good1AfterMerged, (equalTo(good2)));
    }
}