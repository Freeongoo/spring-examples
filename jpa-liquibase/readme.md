# Example use liquibase for spring boot

## Config

1. `cp src/main/resources/application.properties.dist src/main/resources/application.properties`
2. create db for mysql and set correct user and password
3. `cp src/test/resources/application.properties.dist src/test/resources/application.properties`

## Important changes in boot 2.x

In properties file change `liquibase.change-log` to `spring.liquibase.change-log`

See for more details:  
`https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Configuration-Changelog`

## Run

Starting the application will create a database in mysql