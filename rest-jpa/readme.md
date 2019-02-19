# Example jpa rest

## Configuration

For main and test dirs:
`cp application.properties.dist application.properties`

# Examples of implementations

## With only one entity "Employee", without relations

See controller: `/src/main/java/hello/controller/single/EmployeeController.java`

It should be noted that this controller uses the technique of inserting 
a successfully created or updated object as a link to the header

JPA Employee:

```
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
```
## With @OneToMany relations

See controllers: `/src/main/java/hello/controller/oneToMany`

# Exception Handler

- Create enum for list of errors code: `ErrorCode`
- Create base exception `BaseException` for customization response data
- All exceptions which throw from `service` or `controller` must be extended from `BaseException` - for correct handle
- Create advice for exceptions: `ExceptionControllerAdvice`

# Testing MVC

## 1. MockMvc - not run all server - only web layer

### 1.1. MockMvc with a specific controller

Example: `/test/java/hello/mockMvc/controller/EmployeeControllerTest.java`

The disadvantage of this approach is that if we have a global interceptor (`EmployeeNotFoundAdvice`), we need to manually specify it in MockMvcBuilders

```
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class EmployeeControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private EmployeeController employeeController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .setControllerAdvice(new ExceptionControllerAdvice()) // set advice Exception
                .build();       
    }
}
```

or more modern way:

```
@RunWith(SpringRunner.class)
@WebMvcTest(AbstractApiController.class)
@Transactional
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
}
```

### 1.2. MockMvc with WebApplication

Example: `/test/java/hello/mockMvc/webApplication/EmployeeControllerWebApplicationTest.java`

```
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }
```

## 2. Run real server and test by RestTemplate

```
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
```

It is important to remember that in this case you need to clean the database 
yourself after each method, since the `@Transaction` does not work here (not rollback).

Example: `/test/java/hello/restTemplate/EmployeeRestTemplateTest.java`