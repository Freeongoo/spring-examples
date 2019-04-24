package hello.entity.bidirectional.mappedBy;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import org.junit.Test;

import javax.persistence.PersistenceException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@DatabaseSetup("/user_role.xml")
public class RoleTest extends AbstractJpaTest {

    @Test
    public void getUserFromRole() {
        Role role = entityManager.find(Role.class, 1L);
        User user = role.getUser();

        assertThat(user.getId(), equalTo(1L));
    }

    @Test(expected = IllegalStateException.class)
    public void createRoleWithUser_WhenUserNotSaved_WhenSetToRole_ShouldThrowException() {
        User user = new User("NewUser");
        Role role = new Role("NewRole");

        role.setUser(user);

        Role roleFromDb = entityManager.persistFlushFind(role);
    }

    @Test
    public void createRoleWithUser_WhenUserSaved_WhenSetToRole_ShouldRelationNotCreated() {
        User user = new User("NewUser");
        Role role = new Role("NewRole");

        entityManager.persistAndFlush(user);

        role.setUser(user);

        Role roleFromDb = entityManager.persistFlushFind(role);
        assertThat(roleFromDb.getUser(), nullValue());
    }

    @Test
    public void createRoleWithUser_WhenUserSaved_WhenSetToUser_ShouldRelationCreated() {
        User user = new User("NewUser");
        Role role = new Role("NewRole");

        entityManager.persistAndFlush(user);

        user.setRole(role); // owner

        Role roleFromDb = entityManager.persistFlushFind(role);
        assertThat(roleFromDb.getUser(), is(notNullValue()));
    }

    @Test
    public void createRoleWithUser_WhenUserSaved_WhenSetToRoleAndUser_ShouldRelationCreated() {
        User user = new User("NewUser");
        Role role = new Role("NewRole");

        entityManager.persistAndFlush(user);

        role.setUser(user);
        user.setRole(role); // owner

        Role roleFromDb = entityManager.persistFlushFind(role);
        assertThat(roleFromDb.getUser(), is(notNullValue()));
    }

    @Test(expected = PersistenceException.class)
    public void deleteRole_ShouldThrowException() {
        Role role = entityManager.find(Role.class, 1L);
        entityManager.remove(role);
        flushAndClean();
    }

    @Test
    public void deleteRole_WhenBeforeDeleteAllRelationsUsers_ShouldBeOk() {
        Role role = entityManager.find(Role.class, 1L);
        User user = role.getUser();
        entityManager.remove(user);

        entityManager.remove(role);
        flushAndClean();

        Role roleFromDb = entityManager.find(Role.class, 1L);
        assertThat(roleFromDb, equalTo(null));
    }

    @Test
    public void deleteRole_WhenBeforeDeleteAllRelations_ShouldBeOk() {
        Role role = entityManager.find(Role.class, 1L);
        User user = role.getUser();
        // delete only relations not users
        user.setRole(null);

        entityManager.remove(role);
        flushAndClean();

        Role roleFromDb = entityManager.find(Role.class, 1L);
        assertThat(roleFromDb, equalTo(null));
    }
}