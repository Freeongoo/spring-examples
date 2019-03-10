package hello;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="/application-test.properties")
@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@Transactional
public abstract class BaseTest {

    protected Session session;

    @PersistenceContext
    protected EntityManager em;

    /*@Autowired
    protected TestEntityManager entityManager;*/

    private static final String[] DB_UNIT_SET_UP = {"",
            "****************************************************************",
            "*************** DATABASE HAS BEEN ALREADY SET UP ***************",
            "****************************************************************"
    };

    @Before
    public void dbAllSet() {
        Arrays.stream(DB_UNIT_SET_UP).forEach(System.out::println);
        session = em.unwrap(Session.class);
    }

    protected void flushAndClean() {
        em.flush();
        em.clear();
    }
}
