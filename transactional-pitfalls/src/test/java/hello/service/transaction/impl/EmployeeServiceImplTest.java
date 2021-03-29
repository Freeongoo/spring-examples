package hello.service.transaction.impl;

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
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@ContextConfiguration(classes = EmployeeServiceImplTestConfig.class)
@RunWith(SpringRunner.class)
@SqlGroup({
        @Sql("/db.sql"),
})
public class EmployeeServiceImplTest {

    @Autowired
    private EmployeeServiceWithTransactional service;

    @Test
    public void insertListWithTransactional_ShouldCallTransactional() {
        String sameEmail = "email@email.com";
        Employee employee1 = new Employee(1, "dd", sameEmail);
        Employee employee2 = new Employee(2, "dd2", "other@other.ru");
        Employee employee3 = new Employee(3, "dd3", sameEmail);

        // store
        service.insertWithTransactional(employee1);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee2);
        employees.add(employee3);

        // will rollback
        boolean isDuplicateKeyException = false;
        try {
            service.insertListWithTransactional(employees);
        } catch (DuplicateKeyException e) {
            isDuplicateKeyException = true;
            e.printStackTrace();
        }

        assertThat(isDuplicateKeyException, is(true));

        List<Employee> actualList = service.getAll();

        List<Employee> expected = new ArrayList<>();
        expected.add(employee1);

        assertThat(actualList, equalTo(expected));
    }

    @Test
    public void insertListWithoutTransactional_ShouldNotCallTransactional() {
        String sameEmail = "email@email.com";
        Employee employee1 = new Employee(1, "dd", sameEmail);
        Employee employee2 = new Employee(2, "dd2", "other@other.ru");
        Employee employee3 = new Employee(3, "dd3", sameEmail);

        // store
        service.insertWithTransactional(employee1);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee2);
        employees.add(employee3);

        // will rollback
        boolean isDuplicateKeyException = false;
        try {
            service.insertListWithoutTransactional(employees);
        } catch (DuplicateKeyException e) {
            isDuplicateKeyException = true;
            e.printStackTrace();
        }

        assertThat(isDuplicateKeyException, is(true));

        List<Employee> actualList = service.getAll();

        List<Employee> expected = new ArrayList<>();
        expected.add(employee1);
        expected.add(employee2);

        assertThat(actualList, equalTo(expected));
    }

    @Test
    public void insertListWithoutTransactional_ButCallInsertListWithTransactional_ShouldNotCallTransactional() {
        String sameEmail = "email@email.com";
        Employee employee1 = new Employee(1, "dd", sameEmail);
        Employee employee2 = new Employee(2, "dd2", "other@other.ru");
        Employee employee3 = new Employee(3, "dd3", sameEmail);

        // store
        service.insertWithTransactional(employee1);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee2);
        employees.add(employee3);

        // will rollback
        boolean isDuplicateKeyException = false;
        try {
            service.insertListWithoutTransactional_ButCallInsertListWithTransactional(employees);
        } catch (DuplicateKeyException e) {
            isDuplicateKeyException = true;
            e.printStackTrace();
        }

        assertThat(isDuplicateKeyException, is(true));

        List<Employee> actualList = service.getAll();

        List<Employee> expected = new ArrayList<>();
        expected.add(employee1);
        expected.add(employee2);

        assertThat(actualList, equalTo(expected));
    }
}