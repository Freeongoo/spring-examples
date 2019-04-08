package hello.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/** it is preferable to use an abstract class. Here for example only. */
@DatabaseSetup("/data.xml")
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@Transactional
@TestPropertySource(locations="/application-test.properties")
public class EmployeeRepositoryTest {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

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
        entityManager.flush();
        entityManager.clear();

        // then
        List<Employee> all = employeeRepository.findAll();
        assertThat(all.size(), equalTo(3));
    }
}