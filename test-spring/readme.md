# Testing (without @SpringBootTest)

Difference: 
- @ContextConfiguration is an annotation from the Spring Test Framework, which is suitable for every Spring application, 
- @SpringApplicationConfiguration is from Spring Boot and is actually a composite annotation, which includes ContextConfiguration with the custom SpringApplicationContextLoader as loader.

Configuration db

1. create database see `src/main/resources/db.sql`
2. `cp src/main/resources/application.properties.dist src/main/resources/application.properties`
3. `cp src/test/resources/application.properties.dist src/test/resources/application.properties`
4. set db config in application.properties

## Unit testing POJO component - bare jUnit, Mockito (no Spring config, no @Autowired)

see Test:
```
/src/test/java/junit/EmployeeTest.java
```

## Unit testing with Spring annotation (ex.: @Autowired) but with mocking

see Test:  
```
/src/test/java/mockito_with_spring/MyServiceTest.java
```

## Integration test without mocking - get dependencies from Spring

For create test with spring features:
1. `@RunWith(SpringRunner.class)` is used to provide a bridge between Spring Boot test features and JUnit. Whenever we are using any Spring Boot testing features in out JUnit tests, this annotation will be required.
2. Add `ConfigClass` - where configuration @Bean (or use main Application for scan all package)

Example `ConfigClass`:
```
@Configuration
@ComponentScan("hello.simplelogic")
public class MyServiceTestConfig {

    @Bean
    public MyService myService() {
        return new MyService();
    }
}
```

#### Local package scan Config

see `/src/test/java/integration.with_config_local/MyServiceTest.java`

#### Global package scan Config

see `/src/test/java/integration.with_config_main_package/EmployeeDaoImplTest.java`

#### Use main Application for scan (without ConfigClass)

Don't do this - not works with others tests!
`@ContextConfiguration(classes = Application.class)`

#### Import common config file 

So as not to create different configuration files for all tests - you can create common for many tests.   
Or create an abstract test class with a configuration class

see `/src/test/java/integration.spring_integration_with_common_config/MyServiceTest.java`