package hello.entity.removeCascade.orphan;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.PersistenceException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@DatabaseSetup("/removeCascade/orphan/client_account_orphan.xml")
public class ClientOrphanTest extends AbstractJpaTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void deleteClient() {
        ClientOrphan client = entityManager.find(ClientOrphan.class, 1L);

        // not need clean dependencies in GoodOrphan - because set CascadeType.REMOVE
        entityManager.remove(client);

        flushAndClean();

        ClientOrphan clientAfterDelete = entityManager.find(ClientOrphan.class, 1L);
        assertThat(clientAfterDelete, equalTo(null));

        AccountOrphan account1 = entityManager.find(AccountOrphan.class, 1L);
        assertThat(account1, equalTo(null));

        AccountOrphan account2 = entityManager.find(AccountOrphan.class, 2L);
        assertThat(account2, equalTo(null));
    }

    @Test
    public void tryDeleteClientDependenciesByCleanOnlyCollections_WhenSetNullCollections_ShouldThrowException() {
        this.thrown.expect(PersistenceException.class);
        this.thrown.expectMessage("org.hibernate.HibernateException: A collection with cascade=\"all-delete-orphan\" was no longer referenced by the owning entity instance: hello.entity.removeCascade.orphan.ClientOrphan.accountOrphanList");

        ClientOrphan ClientOrphan = entityManager.find(ClientOrphan.class, 1L);

        ClientOrphan.setAccountOrphanList(null); // cannot do this if orphanRemoval = true
        entityManager.persist(ClientOrphan);

        flushAndClean();

        ClientOrphan clientAfterCleanCollection = entityManager.find(ClientOrphan.class, 1L);
        assertThat(clientAfterCleanCollection.getAccountOrphanList().size(), equalTo(2)); // nothing deleted
    }

    @Test
    public void tryDeleteClientDependenciesByCleanOnlyCollections_WhenCleanCollections() {
        ClientOrphan client2 = entityManager.find(ClientOrphan.class, 1L);

        client2.getAccountOrphanList().clear();
        entityManager.persist(client2); // not remove children - because in this case - cascade = remove

        flushAndClean();

        ClientOrphan clientAfterCleanCollection2 = entityManager.find(ClientOrphan.class, 1L);
        assertThat(clientAfterCleanCollection2.getAccountOrphanList().size(), equalTo(2)); // nothing deleted
    }
}