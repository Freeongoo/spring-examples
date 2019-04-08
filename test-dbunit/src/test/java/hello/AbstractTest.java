package hello;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
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
import javax.sql.DataSource;
import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@Transactional
@TestPropertySource(locations="/application-test.properties")
public abstract class AbstractTest {

    private DataSourceDatabaseTester dataSourceDatabaseTester;

    private static IDataSet globalDataSet;

    static {
        try {
            globalDataSet = new FlatXmlDataSetBuilder().build(AbstractTest.class.getResource("/global-data.xml"));
        } catch (DataSetException e) {
            throw new RuntimeException("Cannot read DBUnit file", e);
        }
    }

    @Autowired
    protected DataSource dataSource;

    @PersistenceContext
    protected EntityManager entityManager;

    @Before
    public void setUp() throws Exception {
        dataSourceDatabaseTester = new DataSourceDatabaseTester(dataSource);
        seedData(globalDataSet);
    }

    protected void flushAndClean() {
        entityManager.flush();
        entityManager.clear();
    }

    /**
     * Seed data in to database
     *
     * @param dataSet dataSet
     * @throws Exception Exception
     */
    protected void seedData(IDataSet dataSet) throws Exception {
        dataSourceDatabaseTester.setDataSet(dataSet);
        dataSourceDatabaseTester.onSetup();
    }
}
