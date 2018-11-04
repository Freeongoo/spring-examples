package unit.mockito_bean_without_scan;

import hello.simplelogic.MyService;
import hello.simplelogic.data.Data;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyServiceTestConfig {
    @Bean
    public MyService myService() {
        return new MyService();
    }

    @Bean
    public Data data() {
        return Mockito.mock(Data.class);
    }
}
