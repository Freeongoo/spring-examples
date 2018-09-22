package example.aop.typepointcut.clazz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlowerTest {
    @Autowired
    private Flower flower;

    @Test
    public void touch() {
        flower.touch();
    }
}