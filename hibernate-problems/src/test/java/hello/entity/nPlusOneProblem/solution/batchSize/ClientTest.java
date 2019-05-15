package hello.entity.nPlusOneProblem.solution.batchSize;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import hello.repository.nPlusOneProblem.solution.batchSize.ClientRepository;
import hello.sqltracker.AssertSqlCount;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

// see in Client @BatchSize(size = 25)
@DatabaseSetup({"/client.xml", "/account.xml"})
public class ClientTest extends AbstractJpaTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void solutionNPlusOneProblem() {
        List<Client> clients = clientRepository.findAll();
        System.out.println("get all posts, not run select comments - Lazy");

        for (Client client: clients) {
            List<Account> accounts = client.getAccounts();
            accounts.forEach(a -> System.out.println(a.getName()));
        }

        AssertSqlCount.assertSelectCount(2);
    }
}