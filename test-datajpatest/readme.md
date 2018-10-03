# Test with @DataJpaTest

@DataJpaTest provides some standard setup needed for testing the persistence layer:
* configuring H2, an in-memory database
* setting Hibernate, Spring Data, and the DataSource
* performing an @EntityScan
* turning on SQL logging

The TestEntityManager provided by Spring Boot is an alternative to the standard JPA EntityManager that provides methods commonly used when writing tests.

@DataJpaTest replaces your configured datasource by an in-memory data source.
