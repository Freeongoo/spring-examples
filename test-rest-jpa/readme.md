# Testing Spring Boot REST Jpa Repository

For testing use:

* DBUnit for insert data before test
* JpaRepository for get data from DB
* MockMvc - for test REST controller
* RestTemplate - for test REST endpoints with full start application

## Config

1. `cp src/main/resources/application.properties.dist src/main/resources/application.properties`
2. `cp src/test/resources/application.properties.dist src/test/resources/application.properties`

# With MockMvc - not run all server - only web layer

Divided into two large groups: 
- testing a specific controller
    ```
    private MockMvc mockMvc;
    
    @Autowired
    EmployeeController employeeController;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.employeeController)
                .setControllerAdvice(new ExceptionControllerAdvice())
                .build(); // Standalone context
    }
    ```
    
- using a web application
    ```
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }
    ```
    

## With Web Application

### With in-memory db

Example: `/src/test/java/hello/mock_mvc/webApplication/inMemoryDb/EmployeeControllerWebApplicationTest.java`

### With @MockBean

Example: `/src/test/java/hello/mock_mvc/webApplication/mockBean/EmployeeControllerMockBeanTest.java`

## With Autowired Controller

### Without @SpringBootTest but with @DataJpaTest

See example: `/src/test/java/hello/mock_mvc/controller/spring_runner/EmployeeControllerTest.java`

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

See example: `/src/test/java/hello/mock_mvc/controller/spring_boot_test/EmployeeControllerTest.java`

Important! Add configuration to connect to H2 from application.properties

Remove @ContextConfiguration and not need config file, only add `@SpringBootTest`

# With TestRestTemplate - Run real server

It is important to remember that in this case you need to clean the database 
yourself after each method, since the `@Transaction` does not work here (not rollback).

See example: `/src/test/java/hello/rest_template/RandomPortTestRestTemplateExampleTest.java`