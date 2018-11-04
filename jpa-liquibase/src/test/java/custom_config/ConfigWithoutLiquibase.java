package custom_config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigWithoutLiquibase {

    @Bean
    public SpringLiquibase liquibase() {
        System.out.println("load ConfigWithoutLiquibase");
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setShouldRun(false);
        return liquibase;
    }
}
