# Testing Spring Boot 2.x with DBUnit

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

2. Add in memory DB:

```
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
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
    <Employee id="1" name="John" role="admin"/>
    <Employee id="2" name="Mike" role="user"/>
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

## How to configure data insertion in an abstract base test file

To do this, you need to manually parse the DBUnit file and insert it into the setUp method in test abstract file.

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
    
        protected DataSourceDatabaseTester dataSourceDatabaseTester;
    
        @Autowired
        protected DataSource dataSource;
    
        @Before
        public void setUp() throws Exception {
            dataSourceDatabaseTester = new DataSourceDatabaseTester(dataSource);
            IDataSet dataSet = new FlatXmlDataSet(getClass().getResource("/global-data.xml"));
            dataSourceDatabaseTester.setDataSet(dataSet);
            dataSourceDatabaseTester.onSetup();
        }
    }
```

Using in some test:
```
    @DatabaseSetup(value = "/data.xml", type = DatabaseOperation.INSERT)
    public class EmployeeRepositoryFromAbstractTest extends AbstractTest {
    
        @Autowired
        private EmployeeRepository employeeRepository;
    
        @Override
        @Before
        public void setUp() throws Exception {
            super.setUp();
        }
    
        @Test
        public void findFromGlobalInsert() {
            List<Employee> all = employeeRepository.findAll();
            System.out.println(all);
    
            Optional<Employee> employee = employeeRepository.findById(100L);
            assertTrue(employee.isPresent());
        }
    }
```

### Config connect to DB if custom connect

If use custom bean for connect, like this:
```
@Configuration
public class DBConfiguration {

	@Bean
	public DataSource getDataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass(env.getProperty("spring.datasource.dataSourceClassName"));
		dataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUser(env.getProperty("spring.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));
		return dataSource;
	}
```

You must manually config connect in test for DBUnit, like this:

```
@DbUnitConfiguration(databaseConnection="getDataSource")
```

where `getDataSource` - is name of bean

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