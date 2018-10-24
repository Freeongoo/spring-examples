# Testing Spring Boot REST Jpa Repository

1. `cp src/main/resources/application.properties.dist src/main/resources/application.properties`
2. `cp src/test/resources/application.properties.dist src/test/resources/application.properties`

## With MockMvc

### Without @SpringBootTest but with @DataJpaTest

See example: `/src/test/java/hello/mock_mvc/spring_runner/EmployeeControllerTest.java`

Important! With @DataJpaTest add connect to H2 auto - not see in application.properties

Add @ContextConfiguration for scan packages:

```
@ContextConfiguration(classes = ConfigTest.class)
```

and config file:

```
@Configuration
@ComponentScan("hello")
public class ConfigTest { }
```

### With @SpringBootTest  but without @DataJpaTest

See example: `/src/test/java/hello/mock_mvc/spring_boot_test/EmployeeControllerTest.java`

Important! Add configuration to connect to H2 from application.properties

Remove @ContextConfiguration and not need config file, only add `@SpringBootTest`

## With TestRestTemplate

See example: `/src/test/java/hello/rest_template/RandomPortTestRestTemplateExampleTest.java`