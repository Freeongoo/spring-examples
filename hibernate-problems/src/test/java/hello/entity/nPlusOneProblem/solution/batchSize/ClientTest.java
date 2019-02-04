package hello.entity.nPlusOneProblem.solution.batchSize;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.BaseTest;
import hello.entity.nPlusOneProblem.solution.batchSize.Account;
import hello.entity.nPlusOneProblem.solution.batchSize.Client;
import hello.repository.nPlusOneProblem.solution.batchSize.ClientRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@DatabaseSetup({"/client.xml", "/account.xml"})
public class ClientTest extends BaseTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void solutionNPlusOneProblem() {
        List<Client> clients = clientRepository.findAll();
        System.out.println("get all posts, not run select comments - Lazy");

        for (Client client: clients) {
            Set<Account> accounts = client.getAccounts();
            accounts.forEach(a -> System.out.println(a.getName()));
        }
    }
}