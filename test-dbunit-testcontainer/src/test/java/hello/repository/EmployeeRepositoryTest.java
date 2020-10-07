package hello.repository;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.entity.Employee;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testcontainers.containers.MySQLContainer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * It is important to understand that a completely different port for the database is specified here - 3310.
 * In order not to conflict with another test: AbstractJpaTest.java
 *
 *
 *
 * it is preferable to use an abstract class. Here for example only.
 */
@ContextConfiguration(initializers = EmployeeRepositoryTest.Initializer.class)
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

    public static MySQLContainer mysqlContainer;

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext ctx) {
            TestPropertyValues.of(
                    "spring.datasource.url:" + mysqlContainer.getJdbcUrl(),
                    "spring.datasource.username:" + mysqlContainer.getUsername(),
                    "spring.datasource.password:" + mysqlContainer.getPassword())
                    .applyTo(ctx);
        }
    }

    private static void initAndStartTestContainer() {
        mysqlContainer = new MySQLContainer<>("mysql:5.7")
                .withDatabaseName("test")
                .withUrlParam("characterEncoding", "UTF-8")
                .withUrlParam("serverTimezone", "UTC")
                .withUsername("test")
                .withPassword("test")
                .withCreateContainerCmdModifier(cmd -> cmd
                        .withHostName("localhost")
                        .withPortBindings(new PortBinding(Ports.Binding.bindPort(3310), new ExposedPort(3306)))
                );

        mysqlContainer.start();
    }

    @BeforeClass
    public static void setUpClass() {
        initAndStartTestContainer();
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
        entityManager.flush();
        entityManager.clear();

        // then
        List<Employee> all = employeeRepository.findAll();
        assertThat(all.size(), equalTo(3));
    }
}