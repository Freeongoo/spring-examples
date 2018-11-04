package unit.mockito_spring_runner;

import commonconfig.CommonConfig;
import hello.simplelogic.MyServiceWithTwoDeps;
import hello.simplelogic.data.Data;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

// when use SpringRunner.class - it's work @Autowiring
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CommonConfig.class)
public class MyServiceWithTwoDepsTest {
    @Mock
    private Data mockData;

    @Autowired
    @InjectMocks
    private MyServiceWithTwoDeps service;

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