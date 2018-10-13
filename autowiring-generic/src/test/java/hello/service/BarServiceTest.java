package hello.service;

import hello.dao.GeneralDao;
import hello.model.Bar;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BarServiceTest {
    @Mock
    private GeneralDao<Bar> barDao;

    @InjectMocks
    private GeneralService<Bar> barService = new BarService();

    @Test
    public void get() {
        Bar bar = new Bar();
        bar.setId(1L);
        when(barDao.get(1L)).thenReturn(bar);

        Bar actualBar = barService.get(1L);
        assertThat(actualBar, equalTo(bar));
    }
}