package spring_integration_with_common_config;

import commonconfig.CommonConfig;
import hello.simplelogic.MyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@Import(CommonConfig.class)
public class MyServiceTest {
    @Autowired
    private MyService service;

    @Test
    public void getMainData() {
        String info = service.getMainData();
        assertThat(info, is("getInfo from Data: info"));
    }
}