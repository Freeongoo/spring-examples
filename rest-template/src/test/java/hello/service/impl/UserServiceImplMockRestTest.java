package hello.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.Application;
import hello.entity.User;
import hello.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.springframework.test.web.client.ExpectedCount.manyTimes;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Using MockRestServiceServer for test api
 */
@RunWith(SpringRunner.class)
@RestClientTest({ UserService.class, Application.class })
public class UserServiceImplMockRestTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;
    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUserId(1L);
        user.setTitle("Title");
        user.setBody("Body");
    }

    @Test
    public void getAll() throws JsonProcessingException {
        List<User> listUser = singletonList(user);
        String usersJson = objectMapper.writeValueAsString(listUser);

        this.server.expect(manyTimes(), requestTo("http://jsonplaceholder.typicode.com/posts"))
                .andRespond(withSuccess(usersJson, MediaType.APPLICATION_JSON));

        List<User> users = userService.getAll();
        Assertions.assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void getById() throws JsonProcessingException {
        String userJson = objectMapper.writeValueAsString(user);

        this.server.expect(manyTimes(), requestTo("http://jsonplaceholder.typicode.com/posts/1"))
                .andRespond(withSuccess(userJson, MediaType.APPLICATION_JSON));

        User user = userService.getById(1L);
        Assertions.assertThat(user).isNotNull();
    }
}