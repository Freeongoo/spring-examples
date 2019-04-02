package hello.entity.bidirectional.mappedBy;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import org.junit.Test;

import java.util.Set;

import static java.util.Collections.singleton;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@DatabaseSetup("/user_role.xml")
public class UserTest extends AbstractJpaTest {

    @Test
    public void getRoleFromUser() {
        User user = entityManager.find(User.class, 1L);

        Set<Role> roles = user.getRoles();

        assertThat(roles.size(), equalTo(2));
        assertThat(roles, containsInAnyOrder(
                hasProperty("id", is(1L)),
                hasProperty("id", is(2L))
        ));
    }

    @Test(expected = IllegalStateException.class)
    public void createUserWithRole_WhenRoleNotSaved_WhenSetToUser_ShouldThrowException() {
        User user = new User("NewUser");
        Role role = new Role("NewRole");

        user.setRoles(singleton(role));  // owner

        entityManager.persistAndFlush(user);
    }

    @Test(expected = IllegalStateException.class)
    public void createUserWithRole_WhenRoleNotSaved_WhenSetToUserAndRole_ShouldThrowException() {
        User user = new User("NewUser");
        Role role = new Role("NewRole");

        user.setRoles(singleton(role));  // owner
        role.setUsers(singleton(user));

        entityManager.persistAndFlush(user);
    }

    @Test
    public void createUserWithRole_WhenRoleSaved_WhenSetToUser_ShouldRelationCreated() {
        User user = new User("NewUser");
        Role role = new Role("NewRole");
        entityManager.persistAndFlush(role);

        user.setRoles(singleton(role));  // owner

        User userFromDb = entityManager.persistFlushFind(user);
        assertThat(userFromDb.getRoles().size(), equalTo(1));
    }

    @Test
    public void createUserWithRole_WhenRoleSaved_WhenSetToRole_ShouldRelationNotCreated() {
        User user = new User("NewUser");
        Role role = new Role("NewRole");
        entityManager.persistAndFlush(role);

        role.setUsers(singleton(user));

        User userFromDb = entityManager.persistFlushFind(user);
        assertThat(userFromDb.getRoles().size(), equalTo(0));
    }

    @Test
    public void createUserWithRole_WhenRoleSaved_WhenSetToUserAndRole_ShouldRelationCreated() {
        User user = new User("NewUser");
        Role role = new Role("NewRole");
        entityManager.persistAndFlush(role);

        user.setRoles(singleton(role));  // owner
        role.setUsers(singleton(user));

        User userFromDb = entityManager.persistFlushFind(user);
        assertThat(userFromDb.getRoles().size(), equalTo(1));
    }

    @Test
    public void deleteUser_ShouldRelationDeleted() {
        User user = entityManager.find(User.class, 1L);
        entityManager.remove(user);
        flushAndClean();

        // check than role not deleted with user
        Role role = entityManager.find(Role.class, 1L);
        assertThat(role, is(notNullValue()));

        // check removed relation
        Set<User> users = role.getUsers();
        assertThat(users.size(), equalTo(1));
        assertThat(users, containsInAnyOrder(
                hasProperty("id", is(2L))
        ));
    }
}