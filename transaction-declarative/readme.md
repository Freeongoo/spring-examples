# Example use declarative transaction

For service layer - for class

`/src/main/java/hello/service/transaction/impl/EmployeeServiceImpl.java`

## Configuration

1. cp src/main/resources/application.properties.dist src/main/resources/application.properties
2. cp src/test/resources/application.properties.dist src/test/resources/application.properties

## Test transaction

### With transaction

`/src/test/java/hello/service/transaction/impl/EmployeeServiceImplTest.java`

### Without transaction

`/src/test/java/hello/service/without_transaction/impl/EmployeeServiceImplTest.java`