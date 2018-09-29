package unit.mockito_bean_with_scan;

import hello.simplelogic.MyService;
import hello.simplelogic.data.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MyServiceTestConfig.class)
public class MyServiceTest {
    @Autowired
    private Data data;

    // need autowired because in MyService use annotation @Autowired
    @Autowired
    private MyService service;

    @Test
    public void getMainData() {
        Mockito.when(data.getInfo()).thenReturn("mocked info");
        String info = service.getMainData();
        assertThat(info, is("mocked info"));
    }
}