# Example use liquibase for spring boot

Do not forget for inserting data or using usual sql queries add tag `<rollback>`, otherwise, the rollback will not work.

## Config

1. `cp src/main/resources/liquibase.properties.dist src/main/resources/liquibase.properties`
2. `cp src/main/resources/application.properties.dist src/main/resources/application.properties`
3. create db for mysql and set correct user and password
4. `cp src/test/resources/application.properties.dist src/test/resources/application.properties`

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
        <changeLogFile>src/main/resources/db/liquibase-changelog.xml</changeLogFile>
        <propertyFile>src/main/resources/liquibase.properties</propertyFile>
        <promptOnNonLocalDatabase>false</promptOnNonLocalDatabase>
    </configuration>
</plugin>
```

### Commands

* mvn liquibase:update
* mvn liquibase:dropAll 
* mvn liquibase:rollback -Dliquibase.rollbackCount=1
* mvn liquibase:rollback -Dliquibase.rollbackTag=release_version_1
* mvn liquibase:update -Dliquibase.toTag=release_version_1