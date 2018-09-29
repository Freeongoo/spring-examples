package unit.mockito_junit_runner;

import hello.simplelogic.MyService;
import hello.simplelogic.data.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MyServiceTest {
    @Mock
    private Data mockData;

    @InjectMocks
    private MyService service;

    @Test
    public void getMainData() {
        when(mockData.getInfo()).thenReturn("ahaha");
        String info = service.getMainData();
        assertThat(info, is("ahaha"));
    }
}