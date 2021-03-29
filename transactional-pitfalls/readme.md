# Example some transactional pitfalls with annotation @Transactional

## Configuration

1. cp src/main/resources/application.properties.dist src/main/resources/application.properties
2. cp src/test/resources/application.properties.dist src/test/resources/application.properties

### 1. Pitfalls - Calling an external method without a transaction calls an internal method with a transaction

When call inner method in same class

Transactional not work if:
1. JDK proxy using

In this example method: `insertListWithoutTransactional_ButCallInsertListWithTransactional` not invoke transactional
```
@Service
public class EmployeeServiceWithTransactional implements EmployeeService {

    @Override
    @Transactional
    public void insertListWithTransactional(List<Employee> employees) {
        for (Employee employee : employees) {
            insertWithTransactional(employee);
        }
    }

    @Override
    public void insertListWithoutTransactional_ButCallInsertListWithTransactional(List<Employee> employees) {
        insertListWithTransactional(employees);
    }

```

#### Example test 

`hello.service.transaction.impl.EmployeeServiceImplTest.insertListWithoutTransactional_ButCallInsertListWithTransactional_ShouldNotCallTransactional`

### Solutions
#### 1. Self inject

```
@Service
public class EmployeeServiceSelfInject implements EmployeeService {

    @Autowired
    private EmployeeServiceSelfInject self;

    @Override
    @Transactional
    public void insertListWithTransactional(List<Employee> employees) {
        for (Employee employee : employees) {
            insertWithTransactional(employee);
        }
    }

    @Override
    public void insertListWithoutTransactional_ButCallInsertListWithTransactional(List<Employee> employees) {
        self.insertListWithTransactional(employees);
    }
}
```

#### Example test 
`hello.service.self.inject.EmployeeServiceSelfInjectTest.insertListWithoutTransactional_ButCallInsertListWithTransactional_ShouldCallTransactional`
