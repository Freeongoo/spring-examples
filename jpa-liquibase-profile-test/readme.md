# Example use liquibase for spring boot with profiles

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

## Profiles 

The concept of context is used in the liquibase for config profiles: https://www.liquibase.org/documentation/contexts.html

### How separate prod and test migration?

For this you need to specify the context in the configuration files:
* for production config in `application.properties`: `spring.liquibase.contexts=prod`
* for test config in `application.properties`: `spring.liquibase.contexts=test`

Further we set up the migration files themselves. 
And if we didn’t specify a context in `<changeSet id="2" author="freeongoo">` ourselves, 
then it will be used regardless of the context chosen.

But if we wiped out the context `<changeSet id="2" author="freeongoo" context="test">`, 
then only for the context selected 
in the config and the migration will be rolled

This is very useful when testing as an alternative to using DBUnit.

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
