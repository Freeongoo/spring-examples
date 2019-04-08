package hello.repository;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractTest;
import hello.entity.Employee;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

@DatabaseSetup(value = "/data.xml", type = DatabaseOperation.INSERT)
public class EmployeeRepositoryFromAbstractTest extends AbstractTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void findFromGlobalInsert() {
        List<Employee> all = employeeRepository.findAll();
        System.out.println(all);

        Optional<Employee> employee = employeeRepository.findById(100L);
        assertTrue(employee.isPresent());
    }

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