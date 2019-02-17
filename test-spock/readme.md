# Spock and Spring Boot

## Configuration

1. Add in `application.properties`: `logging.config=classpath:log4j.properties`
2. Create file `log4j.properties` in dir `resources`

## Add deps

```
<dependency>
    <groupId>org.spockframework</groupId>
    <artifactId>spock-core</artifactId>
    <version>1.2-groovy-2.4</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.spockframework</groupId>
    <artifactId>spock-spring</artifactId>
    <version>1.2-groovy-2.4</version>
    <scope>test</scope>
</dependency>
```

And add plugin for compile groovy:

```
<plugin>
    <groupId>org.codehaus.gmavenplus</groupId>
    <artifactId>gmavenplus-plugin</artifactId>
    <version>1.6</version>
    <executions>
        <execution>
            <goals>
                <goal>compileTests</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## DBUnit for Spock

see: https://github.com/janbols/spock-dbunit

#### Configuration

```
<dependency>
    <groupId>com.github.janbols</groupId>
    <artifactId>spock-dbunit</artifactId>
    <version>0.4</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.dbunit</groupId>
    <artifactId>dbunit</artifactId>
    <version>2.5.4</version>
    <scope>test</scope>
</dependency>
```