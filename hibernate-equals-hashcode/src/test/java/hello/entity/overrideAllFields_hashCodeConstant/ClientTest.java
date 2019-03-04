package hello.entity.overrideAllFields_hashCodeConstant;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractHibernateCheckEqualsHashCodeTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

// DBUnit config:
@DatabaseSetup("/client.xml")
public class ClientTest extends AbstractHibernateCheckEqualsHashCodeTest<Client> {

    @Test
    public void checkTheContentsOfTheSetUnderDifferentStatesOfTheEntity() {
        Client client = new Client("John");
        checkTheContentsOfTheSetUnderDifferentStatesOfTheEntity(client);
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
        flushAndClean();

        assertThat(client1, is(not((equalTo(client2)))));
    }

    @Test
    public void managedReattachSameEntity_ShouldBeEquals() {
        Client client1 = session.get(Client.class, 1L);
        em.detach(client1);
        flushAndClean();

        Client client1AfterMerged = em.merge(client1);
        flushAndClean();

        Client client2 = session.get(Client.class, 1L);

        assertThat(client1AfterMerged, (equalTo(client2)));
    }
}