package hello;

import hello.dao.single.EmployeeDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    public void contextLoads() {
    }

    @Test
    public void daoLoad() {
        assertThat(employeeDao, is(notNullValue()));
    }
}