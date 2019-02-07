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

        entityManager.remove(client);
        entityManager.flush();

        Client clientAfterDelete = entityManager.find(Client.class, 1L);
        assertThat(clientAfterDelete, equalTo(null));

        Account account1 = entityManager.find(Account.class, 1L);
        assertThat(account1, equalTo(null));

        Account account2 = entityManager.find(Account.class, 2L);
        assertThat(account2, equalTo(null));
    }
}