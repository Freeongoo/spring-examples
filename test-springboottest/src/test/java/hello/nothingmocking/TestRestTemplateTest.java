package hello.nothingmocking;

import hello.model.Employee;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
        @Sql("/db.sql"),
})
public class TestRestTemplateTest {
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
    public void getEmployeeById() {
        Employee employeeExpected = new Employee("Foo", "foo@foo.com");

        // First - than return id == 1
        restTemplate.postForEntity(url + "/employees", employeeExpected, Employee.class);
        employeeExpected.setId(1);

        ResponseEntity<Employee> responseEntity = restTemplate.getForEntity(url + "/employees/1", Employee.class);
        Employee employee = responseEntity.getBody();

        assertEquals(employeeExpected, employee);
    }

    @Test
    public void employeeList_WhenEmpty() {
        ResponseEntity<Employee[]> responseObject = restTemplate.getForEntity(url + "/employees", Employee[].class);
        Object[] employees = responseObject.getBody();
        List<?> searchList = Arrays.asList(employees);
        assertEquals(0, searchList.size());
    }

    @Test
    public void employeeList_WhenInserted() {
        // insert
        Employee employee = new Employee("Foo", "foo@foo.com");
        restTemplate.postForEntity(url + "/employees", employee, Employee.class);
        employee.setId(1); // first

        Employee[] expectedArrEmployee = new Employee[1];
        expectedArrEmployee[0] = employee;

        ResponseEntity<Employee[]> responseObject = restTemplate.getForEntity(url + "/employees", Employee[].class);
        Employee[] employees = responseObject.getBody();

        assertArrayEquals(expectedArrEmployee, employees);
    }

    @Test
    public void createEmployee_WhenFirst() {
        ResponseEntity<Employee> responseEntity =
                restTemplate.postForEntity(url + "/employees", new Employee("Foo", "foo@foo.com"), Employee.class);
        HttpHeaders headers = responseEntity.getHeaders();
        List<String> location = headers.get("Location");
        String expectedLocation = url + "/employees/1";

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedLocation, location.get(0));
    }

    @Test
    public void createEmployee_WhenSecond() {
        // First
        restTemplate.postForEntity(url + "/employees", new Employee("Foo", "foo@foo.com"), Employee.class);

        ResponseEntity<Employee> responseEntity =
                restTemplate.postForEntity(url + "/employees", new Employee("Foo2", "foo2@foo.com"), Employee.class);
        HttpHeaders headers = responseEntity.getHeaders();
        List<String> location = headers.get("Location");
        String expectedLocation = url + "/employees/2";

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedLocation, location.get(0));
    }
}