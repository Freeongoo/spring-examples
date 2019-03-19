package hello.controller.springValidation;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.ErrorCode;
import hello.repository.springValidation.PeopleRepository;
import hello.springValidation.People;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;

import static hello.controller.springValidation.PeopleController.PATH;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PeopleControllerRestTemplateTest {

    private String peopleUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PeopleRepository peopleRepository;

    @Before
    public void setUp() {
        String url = "http://localhost:" + port;
        peopleUrl = url + PATH;
    }

    @After
    public void cleanUp() {
        // manually clear db - important when testing by TestRestTemplate
        peopleRepository.deleteAll();
    }

    @Test
    public void create_WhenAllCorrect_ShouldBeStatusOK() {
        // when
        People people = new People(20);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(peopleUrl, people, String.class);

        String body = stringResponseEntity.getBody();
        System.out.println(body);

        assertEquals(HttpStatus.OK, stringResponseEntity.getStatusCode());
    }

    @Test
    public void create_WithNegativeAge_ShouldBeBadRequest() throws IOException {
        // when
        People people = new People(-20);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(peopleUrl, people, String.class);

        String body = stringResponseEntity.getBody();
        System.out.println(body);

        HashMap<String, Object> mapResponse = new ObjectMapper().readValue(body, HashMap.class);

        assertEquals(HttpStatus.BAD_REQUEST, stringResponseEntity.getStatusCode());

        assertThat(mapResponse.get("status"), is("error"));
        assertThat(mapResponse.get("code"), is(ErrorCode.INVALID_PARAMS.getValue()));
        assertThat(mapResponse.get("message"), is("Negative value"));
    }
}