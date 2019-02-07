package hello.entity.removeCascade;

import hello.entity.AbstractBaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account extends AbstractBaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
