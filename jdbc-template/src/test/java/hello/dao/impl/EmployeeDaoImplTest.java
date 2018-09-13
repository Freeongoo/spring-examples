package hello.dao.impl;

import hello.AppConfig;
import hello.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

@ContextConfiguration(classes = AppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SqlGroup({
    @Sql("/db.sql"),
})
public class EmployeeDaoImplTest {
    @Autowired
    EmployeeDaoImpl employeeDao;

    @Test
    public void insertWithReturnInsertedId_WhenFirstEmployeeInsert() {
        int expectedId = 1;
        int expectedEmployeesCount = 1;
        Employee newEmployee = new Employee("dd", "email@email.com");

        // check inserted id
        int insertedId = employeeDao.insertWithReturnInsertedId(newEmployee);
        assertThat(expectedId, equalTo(insertedId));

        // check employee object
        newEmployee.setId(insertedId); // need because when insert not need pass id - id autoincrement
        Employee employeeExpected = employeeDao.getById(insertedId);
        assertThat(newEmployee, equalTo(employeeExpected));

        // check all employees;
        List<Employee> list = employeeDao.getAll();
        assertThat(expectedEmployeesCount, equalTo(list.size()));
    }

    @Test
    public void insertWithReturnInsertedId_WhenTwoEmployeesInserted() {
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
        assertThat(expectedId, equalTo(secondInsertedId));

        // check all employees;
        List<Employee> list = employeeDao.getAll();
        assertThat(expectedList, containsInAnyOrder(list.toArray()));
    }

    @Test(expected = DuplicateKeyException.class)
    public void insertWithReturnInsertedId_WhenInsertSameEmails() {
        Employee employee1 = new Employee(1, "dd", "email@email.com");
        Employee employee2 = new Employee(2, "dd2", "email@email.com");

        employeeDao.insert(employee1);
        employeeDao.insert(employee2);
    }
}