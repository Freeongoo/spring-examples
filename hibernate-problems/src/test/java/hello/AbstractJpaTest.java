package hello;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import hello.sqltracker.AssertSqlCount;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@DataJpaTest()
@TestPropertySource(locations="/application-test.properties")
@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public abstract class AbstractJpaTest {

    @Autowired
    protected TestEntityManager testEntityManager;

    protected Session getSession() {
        EntityManager entityManager = testEntityManager.getEntityManager();
        return entityManager.unwrap(Session.class);
    }

    @Before
    public void setUp() {
        AssertSqlCount.reset();
        System.out.println("\n\n\n************************ Begin Test ************************");
    }

    protected void flushAndClean() {
        System.out.println("\n**** flush and clean ****\n");
        testEntityManager.flush();
        testEntityManager.clear();
    }
}
