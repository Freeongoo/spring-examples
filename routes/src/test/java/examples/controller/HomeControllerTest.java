package examples.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

// In this test, the full Spring application context is started, and real server
// And test request - so slow, need time for run server. But more realistic

// alias for SpringJUnit4ClassRunner
@RunWith(SpringRunner.class)
// The @SpringBootTest annotation tells Spring Boot to go and look for a main configuration class
// (one with @SpringBootApplication for instance), and use that to start a Spring application context.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomeControllerTest {

    @Autowired
    private HomeController controller;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        // check than controller is load...
        assertThat(controller).isNotNull();
    }

    @Test
    public void main() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("Welcome to learn routes in Spring!");
    }
}