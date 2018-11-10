package hello.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

@DatabaseSetup("/data.xml")
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void findByName_WhenExist() {
        Employee actual = employeeRepository.findByName("John");

        // expectation
        Employee expected = new Employee("John", "admin");
        expected.setId(1L);

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void findByName_WhenNotExist() {
        Employee actual = employeeRepository.findByName("NotExist");

        assertNull(actual);
    }
}