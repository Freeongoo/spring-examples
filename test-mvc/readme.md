# Testing MVC

## 1. MockMvc - not run all server - only web layer

### 1.1. MockMvc with a specific controller

Example: `/test/java/hello/mockMvc/controller/EmployeeControllerTest.java`

The disadvantage of this approach is that if we have a global interceptor (`EmployeeNotFoundAdvice`), we need to manually specify it in MockMvcBuilders

```
    private MockMvc mockMvc;

    @Autowired
    private EmployeeController employeeController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .setControllerAdvice(new EmployeeNotFoundAdvice()) // set advice Exception
                .build();       
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