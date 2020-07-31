package hello.service.impl;

import hello.entity.User;
import hello.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Run test with real API - bad idea
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="/application-test.properties")
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void getAll() {
        List<User> users = userService.getAll();
        Assertions.assertThat(users.size()).isGreaterThan(1);
    }

    @Test
    public void getById() {
        User user = userService.getById(1L);
        Assertions.assertThat(user).isNotNull();
    }
}