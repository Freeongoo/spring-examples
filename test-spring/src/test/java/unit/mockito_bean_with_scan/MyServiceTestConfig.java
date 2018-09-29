package unit.mockito_bean_with_scan;

import hello.simplelogic.data.Data;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan("hello.simplelogic")
public class MyServiceTestConfig {
    @Bean
    @Primary
    public Data data() {
        return Mockito.mock(Data.class);
    }
}
