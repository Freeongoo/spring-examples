package hello.entity.defaultHashCode;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractHibernateCheckEqualsHashCodeTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

// DBUnit config:
@DatabaseSetup("/good.xml")
public class GoodTest extends AbstractHibernateCheckEqualsHashCodeTest<Good> {

    @Test
    public void checkTheContentsOfTheSetUnderDifferentStatesOfTheEntity() {
        Good good = new Good("John");
        checkTheContentsOfTheSetUnderDifferentStatesOfTheEntity(good);
    }

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
        flushAndClean();

        assertThat(good1, is(not((equalTo(good2)))));
    }

    @Test
    public void managedReattachSameEntity_ShouldBeEquals() {
        Good good1 = session.get(Good.class, 1L);
        em.detach(good1);
        flushAndClean();

        Good good1AfterMerged = em.merge(good1);
        flushAndClean();

        Good good2 = session.get(Good.class, 1L);

        assertThat(good1AfterMerged, (equalTo(good2)));
    }
}