package hello.entity.removeCascade;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@DatabaseSetup("/removeCascade/client_account.xml")
public class ClientTest extends AbstractJpaTest {

    @Test
    public void deleteClient() {
        Client client = entityManager.find(Client.class, 1L);

        // not need clean dependencies in Account - because set CascadeType.REMOVE
        entityManager.remove(client);

        flushAndClean();

        Client clientAfterDelete = entityManager.find(Client.class, 1L);
        assertThat(clientAfterDelete, equalTo(null));

        Account account1 = entityManager.find(Account.class, 1L);
        assertThat(account1, equalTo(null));

        Account account2 = entityManager.find(Account.class, 2L);
        assertThat(account2, equalTo(null));
    }

    @Test
    public void tryDeleteClientDependenciesByCleanOnlyCollections_WhenSetNullCollections() {
        Client client = entityManager.find(Client.class, 1L);

        client.setAccounts(null);
        entityManager.persist(client);

        flushAndClean();

        Client clientAfterCleanCollection = entityManager.find(Client.class, 1L);
        assertThat(clientAfterCleanCollection.getAccounts().size(), equalTo(2)); // nothing deleted
    }

    @Test
    public void tryDeleteClientDependenciesByCleanOnlyCollections_WhenCleanCollections() {
        Client client2 = entityManager.find(Client.class, 1L);

        client2.getAccounts().clear();
        entityManager.persist(client2);

        flushAndClean();

        Client clientAfterCleanCollection2 = entityManager.find(Client.class, 1L);
        assertThat(clientAfterCleanCollection2.getAccounts().size(), equalTo(2)); // nothing deleted
    }
}