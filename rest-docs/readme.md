# Spring REST Docs

## Generation

### Config

Add in test `/src/test/resources/application.properties`

```
# for pretty json output - only for testing
spring.jackson.serialization.indent_output=true
```

It is very important to add this parameter so that the result 
is formatted for readability when generating documentation.

### 1. Install deps

```
<dependency>
    <groupId>org.springframework.restdocs</groupId>
    <artifactId>spring-restdocs-mockmvc</artifactId>
    <scope>test</scope>
</dependency>
```

### 2. Create source index docs: `/src/main/asciidoc/index.adoc`

Using `:source-highlighter: highlightjs` soo cool!

### 3. Create test with Docs

#### 3.1. Create integration test with MvcMock: `HomeControllerTest`

#### 3.2. Add custom dir for docs in tests:
```
    @AutoConfigureRestDocs(outputDir = "target/snippets")
```
#### 3.3. Add in test when response - generate docs:
```
    ResultActions resultActions = this.mockMvc
            .perform(
                    get("/")
                            .accept(APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Hello World")))
            .andDo(print());

    // added for rest docs !!!
    resultActions
            .andDo(document("home", responseFields(
                    fieldWithPath("message").description("The welcome message for the user.")
            )));
```
(where `home` - identificator for name folder)

#### 3.4. Check dir with snippets: `/target/generated-snippets/home`

### 4. Add mvn plugin and config with out custom dirs:

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

### 5. Run 
```
mvn package
```

### 6. Open `/target/generated-docs/index.html`