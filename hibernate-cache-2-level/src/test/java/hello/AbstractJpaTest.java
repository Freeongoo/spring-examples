package hello;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import hello.sqltracker.AssertSqlCount;
import net.sf.ehcache.CacheManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="/application-test.properties")
@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public abstract class AbstractJpaTest {

    protected Session session;

    @PersistenceContext
    protected EntityManager entityManager;

    @Before
    public void setUp() {
        AssertSqlCount.reset();
        System.out.println("\n\n\n************************ Begin Test ************************");

        session = entityManager.unwrap(Session.class);
    }

    protected void flushAndClean() {
        System.out.println("\n**** flush and clean ****\n");
        entityManager.flush();
        entityManager.clear();
    }
}
