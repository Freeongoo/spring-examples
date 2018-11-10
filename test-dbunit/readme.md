# Testing Spring Boot with DBUnit

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