package example.aop.method;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.UndeclaredThrowableException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    @Autowired
    private User user;

    @Test
    public void getName() {
        String name = "test";
        user.setName(name);

        assertThat(user.getName(), equalTo(name));
    }

    @Test
    public void getAge() {
        int age = 20;
        user.setAge(age);

        assertThat(user.getAge(), equalTo(age));
    }

    // UndeclaredThrowableException instead NotAllowAccessCheckedException because
    // NotAllowAccessCheckedException is checked exception so your exception is wrapped with UndeclaredThrowableException, which is unchecked
    @Test(expected = UndeclaredThrowableException.class)
    public void getPassword() {
        String pass = "password";
        user.setPassword(pass);

        assertThat(user.getPassword(), equalTo(pass));
    }
}