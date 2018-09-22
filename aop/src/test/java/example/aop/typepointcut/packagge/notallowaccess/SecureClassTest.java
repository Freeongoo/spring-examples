package example.aop.typepointcut.packagge.notallowaccess;

import example.exceptions.NotAllowAccessRuntimeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecureClassTest {
    @Autowired
    private SecureClass secureClass;

    @Test(expected = NotAllowAccessRuntimeException.class)
    public void getPassword() {
        secureClass.getPassword();
    }

    @Test(expected = NotAllowAccessRuntimeException.class)
    public void getPass() {
        secureClass.getPass();
    }
}