package hello.service.without_transaction.impl;

import hello.model.Employee;
import hello.service.without_transaction.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

@ContextConfiguration(classes = EmployeeServiceImplTestConfig.class)
@RunWith(SpringRunner.class)
@SqlGroup({
        @Sql("/db.sql"),
})
public class EmployeeServiceImplTest {
    @Autowired
    @Qualifier("EmployeeServiceWithoutTransaction")
    private EmployeeService service;

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
    public void insertList_WhenDuplicateByEmail() {
        String sameEmail = "email@email.com";
        Employee employee1 = new Employee(1, "dd", sameEmail);
        Employee employee2 = new Employee(2, "dd2", sameEmail);

        List<Employee> expectedList = new ArrayList<>();
        expectedList.add(employee1);

        // will not rollback
        try {
            service.insertList(expectedList);
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }

        List<Employee> actualList = service.getAll();

        assertThat(actualList, containsInAnyOrder(expectedList.toArray()));
    }
}