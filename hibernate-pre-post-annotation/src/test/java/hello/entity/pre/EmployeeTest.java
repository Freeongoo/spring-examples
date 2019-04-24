package hello.entity.pre;

import hello.AbstractJpaTest;
import hello.repository.pre.EmployeeRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EmployeeTest extends AbstractJpaTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void persistEntityManager_WhenNew() {
        Employee employee = new Employee();
        employee.setName("New");

        entityManager.persist(employee);
        flushAndClean();

        assertThat(employee.getHashNameAndNumber(), is(notNullValue()));
    }

    @Test(expected = PersistenceException.class)
    public void persistEntityManager_WhenTryPersistDuplicate_ShouldThrowException() {
        Employee employee1 = new Employee();
        employee1.setName("New");

        Employee employee2 = new Employee();
        employee2.setName("New");

        entityManager.persist(employee1);
        entityManager.persist(employee2);
        flushAndClean();
    }

    @Test
    public void persistSession_WhenNew() {
        Employee employee = new Employee();
        employee.setName("New");

        session.persist(employee);
        flushAndClean();

        assertThat(employee.getHashNameAndNumber(), is(notNullValue()));
    }
}