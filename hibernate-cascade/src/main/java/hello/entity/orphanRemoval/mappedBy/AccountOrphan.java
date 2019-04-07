package hello.entity.orphanRemoval.mappedBy;

import hello.entity.AbstractBaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "account_orphan")
public class AccountOrphan extends AbstractBaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_orphan_id")
    private ClientOrphan clientOrphan;

    public ClientOrphan getClientOrphan() {
        return clientOrphan;
    }

    public void setClientOrphan(ClientOrphan clientOrphan) {
        this.clientOrphan = clientOrphan;
    }
}
