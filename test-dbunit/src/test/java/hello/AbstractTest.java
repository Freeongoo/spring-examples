package hello;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

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
public abstract class AbstractTest {

    protected DataSourceDatabaseTester dataSourceDatabaseTester;

    @Autowired
    protected DataSource dataSource;

    @Before
    public void setUp() throws Exception {
        dataSourceDatabaseTester = new DataSourceDatabaseTester(dataSource);
        IDataSet dataSet = new FlatXmlDataSet(getClass().getResource("/global-data.xml"));
        dataSourceDatabaseTester.setDataSet(dataSet);
        dataSourceDatabaseTester.onSetup();
    }
}
