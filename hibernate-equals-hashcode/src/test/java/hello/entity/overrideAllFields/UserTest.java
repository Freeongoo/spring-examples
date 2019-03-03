package hello.entity.overrideAllFields;

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
@DatabaseSetup("/user.xml")
public class UserTest extends BaseTest {

    @Test
    public void storeToSetBeforePersist_ShouldBeContains() {
        Set<User> map = new HashSet<>();
        User user1 = new User("John");
        User user2 = new User("Mike");
        map.add(user1);
        map.add(user2);

        em.persist(user1);
        em.persist(user2);

        assertTrue("The entity is not found in the Set after it's persisted.", map.contains(user1));
    }

    @Test
    public void storeToSetMerge_ShouldBeContains() {
        Set<User> map = new HashSet<>();
        User item = new User("John");
        map.add(item);

        em.persist(item);
        User merge1 = em.merge(item);

        assertTrue("The entity is not found in the Set after it's merged.", map.contains(merge1));
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

        assertThat(user1, is(not((equalTo(user2)))));
    }

    @Test
    public void managedReattachSameEntity_ShouldBeEquals() {
        User user1 = session.get(User.class, 1L);
        em.detach(user1);
        User user1AfterMerged = em.merge(user1);

        User user2 = session.get(User.class, 1L);

        assertThat(user1AfterMerged, (equalTo(user2)));
    }
}