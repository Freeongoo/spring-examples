package hello;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
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
    protected TestEntityManager entityManager;

    @Before
    public void setUp() {
        System.out.println("\n\n\n************************ Begin Test ************************");
    }

    protected void flushAndClean() {
        System.out.println("\n**** flush and clean ****\n");
        entityManager.flush();
        entityManager.clear();
    }
}
