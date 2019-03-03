package hello.entity.overrideAllFields_hashCodeConstant;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.BaseTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

// DBUnit config:
@DatabaseSetup("/client.xml")
public class ClientTest extends BaseTest {

    @Test
    public void storeToSetBeforePersist_ShouldBeContains() {
        Set<Client> map = new HashSet<>();
        Client client1 = new Client("John");
        Client client2 = new Client("Mike");
        map.add(client1);
        map.add(client2);

        session.persist(client1);
        session.persist(client2);

        flushAndClean();

        assertTrue("The entity is not found in the Set after it's persisted.", map.contains(client1));
    }

    @Test
    public void storeToSetMerge_ShouldBeContains() {
        Set<Client> map = new HashSet<>();
        Client item = new Client("John");
        map.add(item);

        em.persist(item);
        flushAndClean();

        Client merge1 = em.merge(item);

        Assert.assertTrue("The entity is not found in the Set after it's merged.", map.contains(merge1));
    }

    @Test
    public void transientOtherEntities_ShouldNotBeEquals() {
        Client client1 = new Client("John");
        Client client2 = new Client("Mike");

        assertThat(client1, is(not(equalTo(client2))));
    }

    @Test
    public void managedOtherEntities_ShouldNotBeEquals() {
        Client client1 = session.get(Client.class, 1L);
        Client client2 = session.get(Client.class, 2L);

        assertThat(client1, is(not(equalTo(client2))));
    }

    @Test
    public void managedSameEntities_ShouldBeEquals() {
        Client client1 = session.get(Client.class, 1L);
        Client client2 = session.get(Client.class, 1L);

        assertThat(client1, (equalTo(client2)));
    }

    @Test
    public void managedToDetachSameEntity_ShouldBeEquals() {
        Client client1 = session.get(Client.class, 1L);
        em.detach(client1);

        Client client2 = session.get(Client.class, 1L);

        assertThat(client1, (equalTo(client2)));
    }

    @Test
    public void transientToManageSameEntity_ShouldNotBeEquals() {
        Client client1 = new Client("John");
        Client client2 = new Client("John");

        em.persist(client2);

        assertThat(client1, is(not((equalTo(client2)))));
    }

    @Test
    public void managedReattachSameEntity_ShouldBeEquals() {
        Client client1 = session.get(Client.class, 1L);
        em.detach(client1);
        Client client1AfterMerged = em.merge(client1);

        Client client2 = session.get(Client.class, 1L);

        assertThat(client1AfterMerged, (equalTo(client2)));
    }
}