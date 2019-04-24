package hello.entity.bidirectional.mappedBy;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import hello.sqltracker.AssertSqlCount;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@DatabaseSetup("/user_role.xml")
public class UserTest extends AbstractJpaTest {

    @Test
    public void getRoleFromUser_WhenGetOnlyId_ShouldBeOneSelect() {
        User user = entityManager.find(User.class, 1L);
        Role role = user.getRole();

        assertThat(role.getId(), is(1L));
        AssertSqlCount.assertSelectCount(1);
    }

    @Test
    public void getRoleFromUser_WhenGetName_ShouldBeTwoSelect() {
        User user = entityManager.find(User.class, 1L);
        Role role = user.getRole();

        assertThat(role.getName(), is("Role#1"));
        AssertSqlCount.assertSelectCount(2);
    }

    @Test(expected = IllegalStateException.class)
    public void createUserWithRole_WhenRoleNotSaved_WhenSetToUser_ShouldThrowException() {
        User user = new User("NewUser");
        Role role = new Role("NewRole");

        user.setRole(role);  // owner

        entityManager.persistAndFlush(user);
    }

    @Test
    public void createUserWithRole_WhenRoleSaved_WhenSetToUser_ShouldRelationCreated() {
        User user = new User("NewUser");
        Role role = new Role("NewRole");
        entityManager.persistAndFlush(role);

        user.setRole(role);  // owner

        User userFromDb = entityManager.persistFlushFind(user);
        assertThat(userFromDb.getRole().getId(), is(notNullValue()));

        AssertSqlCount.assertInsertCount(2);
        AssertSqlCount.assertSelectCount(1);
    }

    @Test
    public void createUserWithRole_WhenRoleSaved_WhenSetToRole_ShouldRelationNotCreated() {
        User user = new User("NewUser");
        Role role = new Role("NewRole");
        entityManager.persistAndFlush(role);

        role.setUser(user);

        User userFromDb = entityManager.persistFlushFind(user);
        assertThat(userFromDb.getRole(), nullValue());
    }

    @Test
    public void createUserWithRole_WhenRoleSaved_WhenSetToUserAndRole_ShouldRelationCreated() {
        User user = new User("NewUser");
        Role role = new Role("NewRole");
        entityManager.persistAndFlush(role);

        user.setRole(role);  // owner
        role.setUser(user);

        User userFromDb = entityManager.persistFlushFind(user);
        assertThat(userFromDb.getRole().getId(), is(notNullValue()));
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
        User userFromRole = role.getUser();
        assertThat(userFromRole, nullValue());
    }
}