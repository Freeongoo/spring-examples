# @Autowired with Generic

see example: `/src/main/java/hello/service/BarService.java`

It is important to remember that when the implementation is @Autowired from the interface, only those methods that are defined in the interface will be available!

## Example by JPA:
```
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByName(String name);
}
```

if we try @Autowired like this:
```
@Autowired
private JpaRepository<Employee, Long> employeeRepository;
```

You cannot access to method `List<Employee> findByName(String name);` because it is implemented in a specific implementation

See examples:
```
/src/main/java/hello/service/EmployeeService.java
/src/main/java/hello/service/EmployeeServiceOnlyCommonRealization.java
```
