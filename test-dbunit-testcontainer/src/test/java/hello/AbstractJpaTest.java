package hello;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import hello.repository.EmployeeRepositoryTest;
import hello.sqltracker.AssertSqlCount;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.BeforeClass;
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
import javax.sql.DataSource;
import javax.transaction.Transactional;

@ContextConfiguration(initializers = AbstractJpaTest.Initializer.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@Transactional
@TestPropertySource(locations="/application-test.properties")
public abstract class AbstractJpaTest {

    private static IDataSet globalDataSet;

    public static MySQLContainer mysqlContainer;

    private JdbcDatabaseTester jdbcDatabaseTester;

    static {

        try {
            globalDataSet = new FlatXmlDataSetBuilder().build(AbstractJpaTest.class.getResource("/global-data.xml"));
        } catch (DataSetException e) {
            throw new RuntimeException("Cannot read DBUnit file", e);
        }
    }

    private static void initAndStartTestContainer() {
        mysqlContainer = new MySQLContainer<>("mysql:5.7")
                .withDatabaseName("test")
                .withUsername("test")
                .withPassword("test")
                .withCreateContainerCmdModifier(cmd -> cmd
                        .withHostName("localhost")
                        .withPortBindings(new PortBinding(Ports.Binding.bindPort(3309), new ExposedPort(3306)))
                );

        mysqlContainer.start();
    }

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

    @Autowired
    protected DataSource dataSource;

    @PersistenceContext
    protected EntityManager entityManager;

    @BeforeClass
    public static void setUpClass() {
        initAndStartTestContainer();
    }

    @Before
    public void setUp() throws Exception {
        AssertSqlCount.reset();

        seedDataToDatabase();
    }

    private void seedDataToDatabase() throws Exception {
        jdbcDatabaseTester = new JdbcDatabaseTester(mysqlContainer.getDriverClassName(), mysqlContainer.getJdbcUrl(), mysqlContainer.getUsername(), mysqlContainer.getPassword());
        jdbcDatabaseTester.setSetUpOperation(DatabaseOperation.REFRESH);
        jdbcDatabaseTester.setTearDownOperation(DatabaseOperation.NONE);
        jdbcDatabaseTester.setDataSet(globalDataSet);
        jdbcDatabaseTester.onSetup();
    }

    protected void flushAndClean() {
        entityManager.flush();
        entityManager.clear();
    }
}