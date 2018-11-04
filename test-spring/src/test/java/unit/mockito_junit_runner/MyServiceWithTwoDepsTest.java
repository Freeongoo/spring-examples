package unit.mockito_junit_runner;

import hello.simplelogic.MyServiceWithTwoDeps;
import hello.simplelogic.data.Data;
import hello.simplelogic.util.ConcatStr;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

// when use MockitoJUnitRunner.class - you must self import all dependencies of @Autowiring
@RunWith(MockitoJUnitRunner.class)
public class MyServiceWithTwoDepsTest {

    @Mock
    private Data mockData;

    @Mock
    private ConcatStr concatStr;

    @InjectMocks
    private MyServiceWithTwoDeps service;

    @Test
    public void getMainData() {
        when(mockData.getInfo()).thenReturn("ahaha");
        //when(concatStr.get(anyString())).thenReturn("123");
        String info = service.getMainData();
        assertThat(info, is("ahaha"));
    }
}