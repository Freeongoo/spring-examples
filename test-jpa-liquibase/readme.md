# Example use liquibase for spring boot

Do not forget for inserting data or using usual sql queries add tag `<rollback>`, otherwise, the rollback will not work.

## Config

1. `cp src/main/resources/liquibase.properties.dist src/main/resources/liquibase.properties`
2. `cp src/main/resources/application.properties.dist src/main/resources/application.properties`
3. create db for mysql and set correct user and password
4. `cp src/test/resources/application.properties.dist src/test/resources/application.properties`
5. `cp src/test/resources/application_without_liquibase.properties.dist src/test/resources/application_without_liquibase.properties`

## Important changes in boot 2.x

In properties file change `liquibase.change-log` to `spring.liquibase.change-log`

See for more details:  
`https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Configuration-Changelog`

## Manipulation liquibase with maven plugin

### Install maven plugin

```
<plugin>
    <groupId>org.liquibase</groupId>
    <artifactId>liquibase-maven-plugin</artifactId>
    <version>3.4.2</version>
    <configuration>
        <driver>com.mysql.jdbc.Driver</driver>
        <propertyFile>src/main/resources/liquibase.properties</propertyFile>
        <promptOnNonLocalDatabase>false</promptOnNonLocalDatabase>
    </configuration>
</plugin>
```

add `liquibase.properties` for maven plugin:

```
liquibase.url=jdbc:mysql://localhost:3306/liquibase?useSSL=false
liquibase.username=root
liquibase.password=****
liquibase.change-log=classpath:/db/changelog/liquibase-changelog.xml
```

### Commands

* mvn liquibase:update
* mvn liquibase:dropAll 
* mvn liquibase:rollback -Dliquibase.rollbackCount=1
* mvn liquibase:rollback -Dliquibase.rollbackTag=release_version_1
* mvn liquibase:update -Dliquibase.toTag=release_version_1

# Testing with liquibase

For testing with liquibase, you need to add test data, on the basis of which will verify the correctness of the test result.
1. Create in test dir new file: `/src/test/resources/db/liquibase-changelog.test.xml`
2. Add this file in application.properties: `spring.liquibase.change-log=classpath:db/liquibase-changelog.test.xml`
3. Add changelog insert files for insert data for testing:
    ```
    /src/test/resources/db/changelog/04-insert-data-authors.xml
    /src/test/resources/db/changelog/04-insert-data-books.xml
    ```
4. In `liquibase-changelog.test.xml` extend `liquibase-changelog.xml` and add insert files with testing data:
    ```
    <databaseChangeLog
            xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
             http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd">
    
        <include file="liquibase-changelog.xml" relativeToChangelogFile="true"/>
        <include file="changelog/04-insert-data-authors.xml" relativeToChangelogFile="true"/>
        <include file="changelog/04-insert-data-books.xml" relativeToChangelogFile="true"/>
    </databaseChangeLog>
    ```

## How run tests without autoload liquibase?

Two way:  
1. set in properties file: `spring.liquibase.enabled=false`

See example property: `application_without_liquibase.properties.dist`  
And test Application Context load: `ApplicationWithoutLiquibaseTest`

2. create config file with annotation @Configuration and bean:
```
@Bean
public SpringLiquibase liquibase() {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setShouldRun(false);
    return liquibase;
}
```

See example: `/src/test/java/custom_config/ConfigWithoutLiquibase.java`  
And test Application Context load: `ApplicationTestWithoutLiquibaseByConfigClassTest`