# Spring REST Docs

## Generate

1. Install deps

```
<dependency>
    <groupId>org.springframework.restdocs</groupId>
    <artifactId>spring-restdocs-mockmvc</artifactId>
    <scope>test</scope>
</dependency>
```

2. Create source index docs:
`/src/main/asciidoc/index.adoc`

3. Add custom dir for docs in tests:
```
@AutoConfigureRestDocs(outputDir = "target/snippets")
```

See `/src/test/java/examples/controller/HomeControllerTest.java`

4. Add mvn plugin and config with out custom dirs:

```
<plugin>
    <groupId>org.asciidoctor</groupId>
    <artifactId>asciidoctor-maven-plugin</artifactId>
    <executions>
        <execution>
            <id>generate-docs</id>
            <phase>prepare-package</phase>
            <goals>
                <goal>process-asciidoc</goal>
            </goals>
            <configuration>
                <sourceDocumentName>index.adoc</sourceDocumentName>
                <backend>html</backend>
                <attributes>
                    <snippets>${project.build.directory}/snippets</snippets>
                </attributes>
            </configuration>
        </execution>
    </executions>
</plugin>
```

5. Run 
```
mvn package
```

6. Open `/target/generated-docs/index.html`