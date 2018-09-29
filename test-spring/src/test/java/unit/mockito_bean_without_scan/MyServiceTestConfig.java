package unit.mockito_bean_without_scan;

import hello.simplelogic.MyService;
import hello.simplelogic.data.Data;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MyServiceTestConfig {
    @Bean
    public MyService myService() {
        return new MyService();
    }

    @Bean
    @Primary
    public Data data() {
        return Mockito.mock(Data.class);
    }
}
