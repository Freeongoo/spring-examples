package example.aop.clazz;

import example.exceptions.NotAllowAccessRuntimeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlowerDontTouchTest {
    @Autowired
    private FlowerDontTouch flowerDontTouch;

    @Test(expected = NotAllowAccessRuntimeException.class)
    public void touch() {
        flowerDontTouch.touch();
    }
}