package hello.dao.impl.with_config_scan;

import hello.dao.EmployeeDao;
import hello.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

@ContextConfiguration(classes = EmployeeDaoImplTestConfig.class)
@RunWith(SpringRunner.class)
@SqlGroup({
    @Sql("/db.sql"),
})
public class EmployeeDaoImplTest {
    @Autowired
    EmployeeDao employeeDao;

    @Test
    public void insert_WhenOneEmployee() {
        int expectedId = 1;
        Employee expectedEmployee = new Employee("dd", "email@email.com");

        List<Employee> expectedList = new ArrayList<>();
        expectedList.add(expectedEmployee);

        // check inserted id
        int insertedId = employeeDao.insertWithReturnInsertedId(expectedEmployee);
        assertThat(insertedId, equalTo(expectedId));

        // check employee object
        expectedEmployee.setId(insertedId); // need because when insert not need pass id - id autoincrement
        Employee employeeActual = employeeDao.getById(insertedId);
        assertThat(employeeActual, equalTo(expectedEmployee));

        // check all employees;
        List<Employee> list = employeeDao.getAll();
        assertThat(list, containsInAnyOrder(expectedList.toArray()));
    }

    @Test
    public void insert_WhenTwoEmployees() {
        int expectedId = 2;

        Employee employee1 = new Employee(1, "dd", "email@email.com");
        Employee employee2 = new Employee(2, "dd2", "email2@email.com");

        List<Employee> expectedList = new ArrayList<>();
        expectedList.add(employee1);
        expectedList.add(employee2);

        // first insert
        employeeDao.insert(employee1);

        // check inserted id
        int secondInsertedId = employeeDao.insertWithReturnInsertedId(employee2);
        assertThat(secondInsertedId, equalTo(expectedId));

        // check all employees;
        List<Employee> list = employeeDao.getAll();
        assertThat(list, containsInAnyOrder(expectedList.toArray()));
    }

    @Test(expected = DuplicateKeyException.class)
    public void insert_WhenSameEmails() {
        Employee employee1 = new Employee(1, "dd", "email@email.com");
        Employee employee2 = new Employee(2, "dd2", "email@email.com");

        employeeDao.insert(employee1);
        employeeDao.insert(employee2);
    }
}