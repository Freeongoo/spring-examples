# Example use declarative transaction

Create two service from one interface `EmployeeService`
- `EmployeeServiceWithTransactional` with annotation @Transactional
- `EmployeeServiceWithoutTransactional` without annotation @Transactional

## Configuration

1. cp src/main/resources/application.properties.dist src/main/resources/application.properties
2. cp src/test/resources/application.properties.dist src/test/resources/application.properties

## Test transaction

### With transaction

`/src/test/java/hello/service/transaction/impl/EmployeeServiceImplTest.java`

### Without transaction

`/src/test/java/hello/service/without_transaction/impl/EmployeeServiceImplTest.java`