package hello.entity.bidirectional.mappedBy;

import hello.entity.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role extends AbstractBaseEntity {

    @OneToOne(mappedBy = "role", fetch = FetchType.LAZY)
    private User user;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
