package hello;

import hello.entity.single.Employee;
import hello.repository.single.EmployeeRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static hello.controller.single.EmployeeController.PATH;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;

/**
 * Simple using RestTemplate for real REST and real run web server
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="/application-test.properties")
public class RestTemplateTest {

    private String employeeUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private EmployeeRepository employeeRepository;

    private RestTemplate restTemplate = new RestTemplate();

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
    public void getById_ShouldReturnEmployee() {
        // given
        Employee employeeExpected = new Employee("Foo", "foo@foo.com");

        restTemplate.postForEntity(employeeUrl, employeeExpected, Employee.class);

        ResponseEntity<Employee[]> responseObject = restTemplate.getForEntity(employeeUrl, Employee[].class);
        Employee[] employees = responseObject.getBody();
        Employee createdEmployee = employees[0];
        Long id = createdEmployee.getId();
        employeeExpected.setId(id);

        // when
        ResponseEntity<Employee> responseEntity = restTemplate.getForEntity(employeeUrl + "/" + id, Employee.class);

        // then
        Employee employee = responseEntity.getBody();
        assertEquals(employeeExpected, employee);
    }

    @Test
    public void getAll_WhenEmpty_ShouldReturnEmpty() {
        // when
        ResponseEntity<Employee[]> responseObject = restTemplate.getForEntity(employeeUrl, Employee[].class);

        // then
        Object[] employees = responseObject.getBody();
        List<?> searchList = Arrays.asList(employees);
        assertEquals(0, searchList.size());
    }

    @Test
    public void getAll_WhenExistEmployees_ShouldReturnList() {
        // given
        Employee employee = new Employee("Foo", "foo@foo.com");
        restTemplate.postForEntity(employeeUrl, employee, Employee.class);

        // when
        ResponseEntity<Employee[]> responseObject = restTemplate.getForEntity(employeeUrl, Employee[].class);

        // then
        Employee[] employees = responseObject.getBody();
        Employee firstEmployee = employees[0];
        assertThat(firstEmployee.getName(), equalTo(employee.getName()));
        assertThat(firstEmployee.getRole(), equalTo(employee.getRole()));
    }

    @Test
    public void create_ShouldReturnCreated() {
        // when
        ResponseEntity<Employee> responseEntity =
                restTemplate.postForEntity(employeeUrl, new Employee("Foo", "foo@foo.com"), Employee.class);

        // then
        HttpHeaders headers = responseEntity.getHeaders();
        List<String> location = headers.get("Location");

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        String actual = location.get(0);
        assertThat(actual, matchesPattern(employeeUrl + "/\\d+"));
    }

    @Test
    public void create_WhenCreateTwoEmployee_ShouldReturnCreated() {
        // given
        restTemplate.postForEntity(employeeUrl, new Employee("Foo", "foo@foo.com"), Employee.class);

        // when
        ResponseEntity<Employee> responseEntity =
                restTemplate.postForEntity(employeeUrl, new Employee("Foo2", "foo2@foo.com"), Employee.class);

        // then
        HttpHeaders headers = responseEntity.getHeaders();
        List<String> location = headers.get("Location");

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        String actual = location.get(0);
        assertThat(actual, matchesPattern(employeeUrl + "/\\d+"));
    }
}
