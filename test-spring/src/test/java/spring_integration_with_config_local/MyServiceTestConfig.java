package spring_integration_with_config_local;

import hello.simplelogic.MyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("hello.simplelogic")
public class MyServiceTestConfig {

    @Bean
    public MyService myService() {
        return new MyService();
    }
}
