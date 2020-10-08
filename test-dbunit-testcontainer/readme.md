# Testing Spring Boot 2.x with DBUnit and TestContainer

## Configuration

For main and test dirs:
```
cp application.properties.dist application.properties
cp application-test.properties.dist application-test.properties
```

## Dependencies

1. Add dependencies for testing spring:

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

2. Add TestContainer with mysql:

```
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>testcontainers</artifactId>
    <version>1.14.3</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>mysql</artifactId>
    <version>1.14.3</version>
    <scope>test</scope>
</dependency>
```

3. Add DBUnit dependencies:

```
<dependency>
    <groupId>com.github.springtestdbunit</groupId>
    <artifactId>spring-test-dbunit</artifactId>
    <version>1.3.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.dbunit</groupId>
    <artifactId>dbunit</artifactId>
    <version>2.5.4</version>
    <scope>test</scope>
</dependency>
```

## Configuration properties

1. `cp src/main/resources/application.properties.dist src/main/resources/application.properties`
2. `cp src/test/resources/application.properties.dist src/test/resources/application.properties`

## How to

1. Create `/src/test/resources/data.xml` file for insert to DB for test:

```
<dataset>
    <employee id="1" name="John" role="admin"/>
    <employee id="2" name="Mike" role="user"/>
</dataset>
```

2. Use this file in test: `@DatabaseSetup("/data.xml")`, all annotations:

```
@DatabaseSetup("/data.xml")
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
```

## Config TestContainer for MySQL

Before starting all tests, you need to create a static variable of our container 
and initialize it (with correct encoding for Russian characters).

```
public static MySQLContainer mysqlContainer;

@BeforeClass
public static void setUpClass() {
    initAndStartTestContainer();
}

private static void initAndStartTestContainer() {
     mysqlContainer = new MySQLContainer<>("mysql:5.7")
            .withUrlParam("characterEncoding", "UTF-8")
            .withUrlParam("serverTimezone", "UTC")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test")
            .withUrlParam("useSSL", "false")
            .withCommand("--character-set-server=utf8mb4", "--collation-server=utf8mb4_unicode_ci");;

     mysqlContainer.start();
}
```

  
It is important to understand that we cannot pass Spring settings from 
properties into our container. Because the initialization is done in a 
static block of code. 
  
Therefore, here it is necessary to rewrite our properties in Spring, 
for example like this:

```
@ContextConfiguration(initializers = AbstractTest.Initializer.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class AbstractTest {

    public static MySQLContainer mysqlContainer;
    ...

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    
        @Override
        public void initialize(ConfigurableApplicationContext ctx) {
            TestPropertyValues.of(
                    "spring.datasource.url:" + mysqlContainer.getJdbcUrl(),
                    "spring.datasource.username:" + mysqlContainer.getUsername(),
                    "spring.datasource.password:" + mysqlContainer.getPassword())
                    .applyTo(ctx);
        }
    }
    
```

## How to configure data insertion in an abstract base test file to TestContainer

To do this for DBUnit, you need to manually parse the DBUnit file and 
insert it into the setUp method in test abstract file.

Example abstract base test file for all tests:
```
    @RunWith(SpringRunner.class)
    @SpringBootTest
    @TestExecutionListeners({
            TransactionalTestExecutionListener.class,
            DependencyInjectionTestExecutionListener.class,
            DbUnitTestExecutionListener.class
    })
    @Transactional
    public abstract class AbstractTest {
    
        private static IDataSet globalDataSet;
        private JdbcDatabaseTester jdbcDatabaseTester;
    
        @Autowired
        protected DataSource dataSource;

        static {
    
            try {
                globalDataSet = new FlatXmlDataSetBuilder().build(AbstractJpaTest.class.getResource("/global-data.xml"));
            } catch (DataSetException e) {
                throw new RuntimeException("Cannot read DBUnit file", e);
            }
        }
    
        @Before
        public void setUp() throws Exception {
            seedDataToDatabase(globalDataSet);
        }
        
        /**
        * Add data before test start
        * 
        * @param dataSet  dataSet
        * @throws Exception Exception
        */
        private void seedDataToDatabase(IDataSet dataSet) throws Exception {
            jdbcDatabaseTester = new JdbcDatabaseTester(mysqlContainer.getDriverClassName(), mysqlContainer.getJdbcUrl(), mysqlContainer.getUsername(), mysqlContainer.getPassword());
            jdbcDatabaseTester.setSetUpOperation(DatabaseOperation.REFRESH);
            jdbcDatabaseTester.setTearDownOperation(DatabaseOperation.NONE);
            jdbcDatabaseTester.setDataSet(dataSet);
            jdbcDatabaseTester.onSetup();
        }
    }
```

## how to work with DBUnit when there is already data in the selected table

You must config `@DatabaseSetup`:

```
    @Test
    public void getAllAuthors_WithoutDBUnit() {
        List<Author> authors = authorsManager.getAllAuthors().collect(Collectors.toList());
        assertFalse(authors.isEmpty());
        assertEquals(5, authors.size());
    }

    @Test
    @DatabaseSetup(value = "/dbunit/books_authors.xml")
    public void getAllAuthors_WithDBUnit_DeleteAllAndInsertOnlyFromDBUnitFile() {
        List<Author> authors = authorsManager.getAllAuthors().collect(Collectors.toList());
        assertFalse(authors.isEmpty());
        assertEquals(1, authors.size());
    }

    @Test
    @DatabaseSetup(value = "/dbunit/books_authors.xml", type = DatabaseOperation.INSERT)
    public void getAllAuthors_WithDBUnit_Add() {
        List<Author> authors = authorsManager.getAllAuthors().collect(Collectors.toList());
        assertFalse(authors.isEmpty());
        assertEquals(6, authors.size());
    }
```

# Important! When used @MockBean

Configuration must be:
```
@TestExecutionListeners(value = {
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
```