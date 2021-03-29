package hello.service.without_transaction.impl;

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

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@ContextConfiguration(classes = EmployeeServiceImplTestConfig.class)
@RunWith(SpringRunner.class)
@SqlGroup({
        @Sql("/db.sql"),
})
public class EmployeeServiceImplTest {

    @Autowired
    private EmployeeServiceWithoutTransactional service;

    @Test
    public void insertList() {
        Employee employee1 = new Employee(1, "dd", "email@email.com");
        Employee employee2 = new Employee(2, "dd2", "email2@email.com");

        List<Employee> expectedList = new ArrayList<>();
        expectedList.add(employee1);
        expectedList.add(employee2);

        service.insertList(expectedList);

        List<Employee> actualList = service.getAll();

        assertThat(actualList, containsInAnyOrder(expectedList.toArray()));
    }

    @Test
    public void insertList_WhenDuplicateByEmail_ShouldInsertOnlyOne() {
        String sameEmail = "email@email.com";
        Employee employee1 = new Employee(1, "dd", sameEmail);
        Employee employee2 = new Employee(2, "dd2", sameEmail);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);

        // will not rollback
        boolean isDuplicateKeyException = false;
        try {
            service.insertList(employees);
        } catch (DuplicateKeyException e) {
            isDuplicateKeyException = true;
            e.printStackTrace();
        }

        assertThat(isDuplicateKeyException, is(true));

        List<Employee> actualList = service.getAll();

        List<Employee> expected = new ArrayList<>();
        expected.add(employee1);

        assertThat(actualList, containsInAnyOrder(expected.toArray()));
    }
}