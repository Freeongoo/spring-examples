package hello.entity.orphanRemoval.mappedBy;

import hello.entity.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "client_orphan")
public class ClientOrphan extends AbstractBaseEntity<Long> {

    @OneToMany(mappedBy = "clientOrphan", orphanRemoval = true)
    private List<AccountOrphan> accountOrphanList;

    public List<AccountOrphan> getAccountOrphanList() {
        return accountOrphanList;
    }

    public void setAccountOrphanList(List<AccountOrphan> accountOrphanList) {
        this.accountOrphanList = accountOrphanList;
    }
}
