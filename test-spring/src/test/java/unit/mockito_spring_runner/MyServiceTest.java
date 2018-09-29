package unit.mockito_spring_runner;

import hello.simplelogic.MyService;
import hello.simplelogic.data.Data;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

// old version - use @RunWith(MockitoJUnitRunner.class)
// with @RunWith(MockitoJUnitRunner.class) not need invoke - MockitoAnnotations.initMocks(this);
@RunWith(SpringRunner.class)
public class MyServiceTest {
    @Mock
    private Data mockData;

    @InjectMocks
    private MyService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getMainData() {
        when(mockData.getInfo()).thenReturn("ahaha");
        String info = service.getMainData();
        assertThat(info, is("ahaha"));
    }
}