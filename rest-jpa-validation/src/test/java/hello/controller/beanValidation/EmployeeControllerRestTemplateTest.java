package hello.controller.beanValidation;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.entity.beanValidation.Employee;
import hello.repository.beanValidation.EmployeeRepository;
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

import static hello.controller.beanValidation.EmployeeController.PATH;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerRestTemplateTest {

    private String employeeUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() {
        String url = "http://localhost:" + port;
        employeeUrl = url + PATH;
    }

    @After
    public void cleanUp() {
        // manually clear db - important when testing by TestRestTemplate
        employeeRepository.deleteAll();
    }

    @Test
    public void create_WhenAllCorrect_ShouldBeStatusOK() {
        // when
        String role = "admin";
        Employee employee = new Employee("John", role);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(employeeUrl, employee, String.class);

        String body = stringResponseEntity.getBody();
        System.out.println(body);

        assertEquals(HttpStatus.OK, stringResponseEntity.getStatusCode());
    }

    @Test
    public void create_WithoutName_ShouldBeBadRequest() throws IOException {
        // when
        String role = "admin";
        Employee employee = new Employee(null, role);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(employeeUrl, employee, String.class);

        String body = stringResponseEntity.getBody();
        System.out.println(body);

        HashMap<String, Object> mapResponse = new ObjectMapper().readValue(body, HashMap.class);

        assertEquals(HttpStatus.BAD_REQUEST, stringResponseEntity.getStatusCode());
        assertThat(mapResponse.get("status"), is(400));
        assertThat(mapResponse.get("error"), equalTo("Bad Request"));
        assertThat(mapResponse.get("timestamp"), is(notNullValue()));
        assertThat(mapResponse.get("errors"), is(notNullValue()));
        assertThat(mapResponse.get("message"), is(notNullValue()));
        assertThat(mapResponse.get("path"), is("/employees"));
    }
}
