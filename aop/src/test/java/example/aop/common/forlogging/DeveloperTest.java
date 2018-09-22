package example.aop.common.forlogging;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeveloperTest {
    @Autowired
    private Developer developer;

    @Test
    public void getName() {
        developer.setName("Name");
        String name = developer.getName();
    }

    @Test
    public void setName() {
        developer.setName("Name");
    }

    @Test
    public void getSpecialty() {
        developer.setSpecialty("Prog");
        String prog = developer.getSpecialty();
    }

    @Test
    public void setSpecialty() {
        developer.setSpecialty("Prog");
    }

    @Test
    public void getExperience() {
        developer.setExperience(12);
        int ex = developer.getExperience();
    }

    @Test
    public void setExperience() {
        developer.setExperience(12);
    }

    @Test(expected = ClassCastException.class)
    public void throwSomeMysticException() {
        developer.throwSomeMysticException();
    }
}