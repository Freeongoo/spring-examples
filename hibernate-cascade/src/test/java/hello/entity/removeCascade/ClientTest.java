package hello.entity.removeCascade;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@DatabaseSetup("/removeCascade/client_account.xml")
public class ClientTest extends AbstractJpaTest {

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // delete objects
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Test
    public void deleteClient_ShouldDeletedRelationObject() {
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
    public void deleteAccount() {
        Account account1 = entityManager.find(Account.class, 1L);
        entityManager.remove(account1);
        flushAndClean();

        Client client = entityManager.find(Client.class, 1L);
        assertThat(client.getAccounts().size(), equalTo(1));
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // delete relations
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Test
    public void tryDeleteClientDependenciesByCleanOnlyCollections_WhenSetNullCollections_ShouldNothingChanged() {
        Client client = entityManager.find(Client.class, 1L);

        client.setAccounts(null);
        entityManager.persist(client);

        flushAndClean();

        Client clientAfterCleanCollection = entityManager.find(Client.class, 1L);
        assertThat(clientAfterCleanCollection.getAccounts().size(), equalTo(2)); // nothing deleted
    }

    @Test
    public void tryDeleteClientDependenciesByCleanOnlyCollections_WhenCleanCollections_ShouldNothingChanged() {
        Client client2 = entityManager.find(Client.class, 1L);

        client2.getAccounts().clear();
        entityManager.persist(client2);

        flushAndClean();

        Client clientAfterCleanCollection2 = entityManager.find(Client.class, 1L);
        assertThat(clientAfterCleanCollection2.getAccounts().size(), equalTo(2)); // nothing deleted
    }

    @Test
    public void tryDeleteClientDependencies_WhenSetNullClientInDependenciesAccounts_ShouldBeDeleted() {
        Client client2 = entityManager.find(Client.class, 1L);

        List<Account> accounts = client2.getAccounts();
        accounts.forEach(a -> a.setClient(null));

        flushAndClean();

        Client clientAfterCleanCollection2 = entityManager.find(Client.class, 1L);
        assertThat(clientAfterCleanCollection2.getAccounts().size(), equalTo(0)); // deleted
    }
}