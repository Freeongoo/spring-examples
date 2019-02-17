# Config Log4j 2.x for Spring Boot 2.x

## Configuration

1. Add in `application.properties`: `logging.config=classpath:log4j.properties`
2. Create file `log4j.properties` in dir `resources`

### Add dependencies

1. Exclude standard logger:
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

2. Add log4j2 starter

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```

## Logging basic

### Info

You can choose different levels of logging.  
List of levels from general to specific:
* TRACE 
* DEBUG 
* INFO 
* WARN
* ERROR
* FATAL
* OFF for disable

For example if you select level "WARN", then show only this levels:
* WARN
* ERROR
* FATAL