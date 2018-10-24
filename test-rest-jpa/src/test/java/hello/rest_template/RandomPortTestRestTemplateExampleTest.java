package hello.rest_template;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.entity.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@DatabaseSetup("/data.xml")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class RandomPortTestRestTemplateExampleTest {
    private String url;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        url = "http://localhost:" + port;
    }

    @Test
    public void one() {
        long id = 1;

        Employee employeeExpected = new Employee("John", "admin");
        employeeExpected.setId(id);

        ResponseEntity<Employee> responseEntity = restTemplate.getForEntity(url + "/employees/" + id, Employee.class);

        Employee employee = responseEntity.getBody();
        assertEquals(employeeExpected, employee);
    }

    @Test
    public void employeeList() {
        ResponseEntity<Employee[]> responseObject = restTemplate.getForEntity(url + "/employees", Employee[].class);
        Object[] employees = responseObject.getBody();
        List<?> searchList = Arrays.asList(employees);
        assertEquals(2, searchList.size());
    }

    @Test
    public void newEmployee() {
        Employee employeeNew = new Employee("John", "admin");
        ResponseEntity<Employee> responseEntity = restTemplate.postForEntity(url + "/employees", employeeNew, Employee.class);

        HttpHeaders headers = responseEntity.getHeaders();
        List<String> location = headers.get("Location");
        String expectedLocation = url + "/employees/3";

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedLocation, location.get(0));

        ResponseEntity<Employee[]> responseObject = restTemplate.getForEntity(url + "/employees", Employee[].class);
        Object[] employees = responseObject.getBody();
        List<?> searchList = Arrays.asList(employees);
        assertEquals(3, searchList.size());
    }
}
