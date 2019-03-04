package hello.entity.overrideAllFields;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractHibernateCheckEqualsHashCodeTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

// DBUnit config:
@DatabaseSetup("/user.xml")
public class UserTest extends AbstractHibernateCheckEqualsHashCodeTest<User> {

    @Test
    public void checkTheContentsOfTheSetUnderDifferentStatesOfTheEntity() {
        User user = new User("John");
        checkTheContentsOfTheSetUnderDifferentStatesOfTheEntity(user);
    }

    @Test
    public void transientOtherEntities_ShouldNotBeEquals() {
        User user1 = new User("John");
        User user2 = new User("Mike");

        assertThat(user1, is(not(equalTo(user2))));
    }

    @Test
    public void managedOtherEntities_ShouldNotBeEquals() {
        User user1 = session.get(User.class, 1L);
        User user2 = session.get(User.class, 2L);

        assertThat(user1, is(not(equalTo(user2))));
    }

    @Test
    public void managedSameEntities_ShouldBeEquals() {
        User user1 = session.get(User.class, 1L);
        User user2 = session.get(User.class, 1L);

        assertThat(user1, (equalTo(user2)));
    }

    @Test
    public void managedToDetachSameEntity_ShouldBeEquals() {
        User user1 = session.get(User.class, 1L);
        em.detach(user1);

        User user2 = session.get(User.class, 1L);

        assertThat(user1, (equalTo(user2)));
    }

    @Test
    public void transientToManageSameEntity_ShouldNotBeEquals() {
        User user1 = new User("John");
        User user2 = new User("John");

        em.persist(user2);
        flushAndClean();

        assertThat(user1, is(not((equalTo(user2)))));
    }

    @Test
    public void managedReattachSameEntity_ShouldBeEquals() {
        User user1 = session.get(User.class, 1L);
        em.detach(user1);
        flushAndClean();

        User user1AfterMerged = em.merge(user1);
        flushAndClean();

        User user2 = session.get(User.class, 1L);

        assertThat(user1AfterMerged, (equalTo(user2)));
    }
}