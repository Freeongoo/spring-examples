package hello.entity.removeCascade;

import hello.entity.AbstractBaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "client")
public class Client extends AbstractBaseEntity<Long> {

    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    private List<Account> accounts;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
