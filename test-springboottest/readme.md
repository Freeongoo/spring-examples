# 1. Mock Testing with @SpringBootTest

See package: `/src/test/java/hello/mocking`

Mocking with `@MockBean`

# 2. Integration Testing with @SpringBootTest

See package: `/src/test/java/hello/nothingmocking`

It's mean than we don't mock anything.

The @SpringBootTest annotation can be used when we need to bootstrap the entire container. The annotation works by creating the ApplicationContext that will be utilized in our tests.

Provides the following features over and above the regular Spring TestContext Framework:
* Uses SpringBootContextLoader as the default ContextLoader when no specific @ContextConfiguration (loader=…) is defined.
* Automatically searches for a @SpringBootConfiguration when nested @Configuration is not used, and no explicit classes are specified.
* Allows custom Environment properties to be defined using the properties attribute.
* Provides support for different web environment modes, including the ability to start a fully running web server listening on a defined or random port.
* Registers a TestRestTemplate and/or WebTestClient bean for use in web tests that are using a fully running web server.

## Installation

1. create database see `src/main/resources/db.sql`
2. `cp src/main/resources/application.properties.dist src/main/resources/application.properties`
2. `cp src/test/resources/application.properties.dist src/test/resources/application.properties` for integration testing
3. set db config in application.properties

## Examples

#### A simple test checks that the context is loaded

see `/src/test/java/hello/ApplicationTest.java`

#### Testing API with MockMvc

(better use @WebMvcTest(RestController.class) but this is for example)

see `/src/test/java/hello/MvcMockTest.java`

#### Testing API with TestRestTemplate

see `/src/test/java/hello/TestRestTemplateTest.java`

#### Testing DAO 

Of course you do not need to run the entire context for testing, but for an example    
see `/src/test/java/hello/dao/EmployeeDaoImplTest.java`