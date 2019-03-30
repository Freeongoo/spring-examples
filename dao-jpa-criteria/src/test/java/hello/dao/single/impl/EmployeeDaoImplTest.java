package hello.dao.single.impl;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.BaseTest;
import hello.dao.single.EmployeeDao;
import hello.entity.single.Employee;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@DatabaseSetup({"/employee.xml"})
public class EmployeeDaoImplTest extends BaseTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    public void persist() {
        Employee employee = new Employee("NewJohn", "User");
        employeeDao.persist(employee);
        flushAndClean();

        assertThat(employee.getId(), is(notNullValue()));
    }

    @Test
    public void getAll() {
        List<Employee> employees = employeeDao.getAll();

        assertThat(employees.size(), equalTo(2));
        assertThat(employees, containsInAnyOrder(
                hasProperty("id", is(1L)),
                hasProperty("id", is(2L))
        ));
    }

    @Test
    public void getById_WhenExist() {
        Optional<Employee> optionalEmployee = employeeDao.getById(1L);
        Employee expectedEmployee = new Employee("John", "admin");
        expectedEmployee.setId(1L);

        assertThat(optionalEmployee, equalTo(Optional.of(expectedEmployee)));
    }

    @Test
    public void getById_WhenNotExist() {
        Optional<Employee> optionalEmployee = employeeDao.getById(-1L);

        assertThat(optionalEmployee, equalTo(Optional.empty()));
    }

    @Test
    public void delete() {
        Employee employee = employeeDao.getById(1L)
                .orElseThrow(() -> new RuntimeException("Try delete not exist Employee"));
        employeeDao.delete(employee);
        flushAndClean();

        List<Employee> employees = employeeDao.getAll();
        assertThat(employees.size(), equalTo(1));
        assertThat(employees, containsInAnyOrder(
                hasProperty("id", is(2L))
        ));
    }

    @Test
    public void findByName_WhenExistName() {
        List<Employee> employees = employeeDao.findByName("John");

        assertThat(employees.size(), equalTo(1));
        assertThat(employees, containsInAnyOrder(
                hasProperty("name", is("John"))
        ));
    }

    @Test
    public void findByName_WhenNotExistName() {
        List<Employee> employees = employeeDao.findByName("notExistName");

        assertThat(employees.size(), equalTo(0));
    }
}