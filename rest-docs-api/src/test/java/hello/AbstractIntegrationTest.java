package hello;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="/application-test.properties")
@Transactional
public abstract class AbstractIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void setUp() {
        //TODO: add something global init for all tests
    }

    protected Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    protected void persistAndFlush(Object obj) {
        getSession().persist(obj);
        flushAndClear();
    }

    protected void flushAndClear() {
        getSession().flush();
        getSession().clear();
    }

    /**
     * get current method name in called method
     *
     * @return method name
     */
    protected static String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }
}
