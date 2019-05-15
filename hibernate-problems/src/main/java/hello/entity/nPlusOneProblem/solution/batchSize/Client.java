package hello.entity.nPlusOneProblem.solution.batchSize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import hello.entity.AbstractBaseEntity;
import org.hibernate.annotations.BatchSize;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "client")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Client extends AbstractBaseEntity<Long> {

    @BatchSize(size = 25)
    @OneToMany(mappedBy = "client")
    private List<Account> accounts;

    public Client() {
    }

    public Client(String name) {
        this.name = name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
