package hello.entity.bidirectional.mappedBy;

import hello.entity.AbstractBaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User extends AbstractBaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
