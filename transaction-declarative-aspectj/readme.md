# Example using @Transactional with @AspectJ

## Configuration

1. cp src/main/resources/application.properties.dist src/main/resources/application.properties
2. cp src/test/resources/application.properties.dist src/test/resources/application.properties

## Enable AspectJ

add dep:

```
  <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjrt</artifactId>
        <version>1.9.2</version>
    </dependency>

    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjtools</artifactId>
        <version>1.9.2</version>
    </dependency>

    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjweaver</artifactId>
        <version>1.9.2</version>
    </dependency>

    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-aspects</artifactId>
    </dependency>
```

add plugin:
```
<plugin>
    <groupId>com.nickwongdev</groupId>
    <artifactId>aspectj-maven-plugin</artifactId>
    <version>1.12.6</version>
    <configuration>
        <complianceLevel>11</complianceLevel>
        <source>11</source>
        <target>11</target>
        <showWeaveInfo>true</showWeaveInfo>
        <verbose>true</verbose>
        <Xlint>ignore</Xlint>
        <encoding>UTF-8</encoding>
        <aspectLibraries>
            <aspectLibrary>
                <groupId>org.springframework</groupId>
                <artifactId>spring-aspects</artifactId>
            </aspectLibrary>
        </aspectLibraries>
        <showWeaveInfo>true</showWeaveInfo>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>compile</goal>
                <goal>test-compile</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

Set transactional using AspectJ

```
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableAspectJAutoProxy
```

## Benefits
### 1. Speed up!
### 2. Calling an external method without a transaction calls an internal method with a transaction

Work well if using AspectJ (if using JDK proxy - not work)

See test for more info