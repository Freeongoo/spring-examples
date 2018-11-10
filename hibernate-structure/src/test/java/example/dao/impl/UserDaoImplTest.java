package example.dao.impl;

import example.dao.UserDao;
import example.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void getByUserName() {
        User user = new User("FirstName", "LastName", "my@my.com", "username", "password", true);
        userDao.persist(user);
        userDao.flush();

        User actualUser = userDao.getByUserName("username");

        assertThat(actualUser, equalTo(user));
    }

    @Test
    public void getByUserName_WhenGetNotExist() {
        User actualUser = userDao.getByUserName("username");

        assertNull(actualUser);
    }
}