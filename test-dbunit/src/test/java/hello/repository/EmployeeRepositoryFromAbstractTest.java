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

/** Using "type" as DatabaseOperation.INSERT for adding data to table "employee" not erase */
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
    public void findFromGlobalInsert_ShouldBeExist() {
        // when
        Optional<Employee> employee = employeeRepository.findById(100L);

        // then
        assertTrue(employee.isPresent());
    }

    @Test
    public void findByName_WhenExist_ShouldBeExist() {
        // given
        Employee expected = new Employee("John", "admin");
        expected.setId(1L);

        // when
        Optional<Employee> actual = employeeRepository.findByName("John");

        // then
        assertThat(actual, equalTo(Optional.of(expected)));
    }

    @Test
    public void findByName_WhenNotExist_ShouldBeNull() {
        // when
        Optional<Employee> actual = employeeRepository.findByName("NotExist");

        // then
        assertThat(actual, equalTo(Optional.empty()));
    }

    @Test
    public void createNew_ShouldBeMore() {
        // given
        Employee employee = new Employee("John", "admin");

        // when
        employeeRepository.save(employee);
        flushAndClean();

        // then
        List<Employee> all = employeeRepository.findAll();
        assertThat(all.size(), equalTo(4));
    }
}