package hello;

import hello.controller.single.EmployeeController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="/application-test.properties")
public class ApplicationTest {

    @Autowired
    private EmployeeController employeeController;

    @Test
    public void contextLoads() {
    }

    @Test
    public void controllerLoad() {
        assertThat(employeeController, is(notNullValue()));
    }
}