# Example jpa rest with update problems 

## Configuration

For main and test dirs:
`cp application.properties.dist application.properties`

# Examples of implementations update

## Classic version

See test: `/src/test/java/hello/controller/classic/EmployeeControllerTest.java`

In controller: 
```
@PatchMapping("/{id}")
public Employee update(@RequestBody Employee employee, @PathVariable Long id) {
    return service.update(id, employee);
}
```

In service:
```
public T update(ID id, T entity) {
    Optional<T> optionalEntityFromDB = getRepository().findById(id);
    return optionalEntityFromDB
            .map(e -> saveAndReturnSavedEntity(entity, e))
            .orElseThrow(getNotFoundExceptionSupplier("Cannot update - not exist entity by id: " + id, OBJECT_NOT_FOUND));
}

private T saveAndReturnSavedEntity(T entity, T entityFromDB) {
    entity.setId(entityFromDB.getId());
    return getRepository().save(entity);
}
```

Pros:
* simple implement
* auto deserialization to entity in controller
* enable add Bean Validation from the box (@Valid @RequestBody Employee employee)

Cons:
* cannot detect passed from json null field or not passed nothing to field
* wrong behavior when there are no fields in json - these fields will be updated to null anyway

## Raw Json version

See test: `/src/test/java/hello/controller/rawJson/EmployeeRawJsonControllerTest.java`

In controller: 
```
@PatchMapping("/{id}")
public Employee update(@RequestBody String json, @PathVariable Long id) {
    return service.update(id, json);
}
```

In service:
```
@Service
public class EmployeeRawJsonService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee update(Long id, String json) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee
                .map(e -> getUpdatedFromJson(e, json))
                .orElseThrow(() -> new ResourceNotFoundException("Not exist by id", OBJECT_NOT_FOUND));
    }

    private Employee getUpdatedFromJson(Employee employee, String json) {
        Long id = employee.getId();

        updateFromJson(employee, json);

        employee.setId(id);
        return employeeRepository.save(employee);
    }

    private void updateFromJson(Employee employee, String json) {
        try {
            new ObjectMapper().readerForUpdating(employee).readValue(json);
        } catch (IOException e) {
            throw new RuntimeException("Cannot update from json", e);
        }
    }
}
```

Pros:
* correct behavior when there are no fields in json - these fields will not be updated

Cons:
* manual deserialization
* cannot auto add Bean Validation from the box

## Reflection

### Reflection update fields written by ourselves

See test: `/src/test/java/hello/controller/reflection/self/EmployeeReflectionSelfControllerTest.java`

In controller:
```
@RestController
@RequestMapping(EmployeeReflectionSelfController.PATH)
public class EmployeeReflectionSelfController {

    public final static String PATH = "/reflection-self-employees";

    @Autowired
    private EmployeeReflectionSelfService service;

    @PatchMapping("/{id}")
    public Employee update(@RequestBody Map<String, Object> requestMap, @PathVariable Long id) {
        return service.update(id, requestMap);
    }
}
```

In service:
```
@Service
public class EmployeeReflectionSelfService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee update(Long id, Map<String, Object> requestMap) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee
                .map(e -> getUpdatedFromMap(e, requestMap))
                .orElseThrow(() -> new ResourceNotFoundException("Not exist by id", OBJECT_NOT_FOUND));
    }

    private Employee getUpdatedFromMap(Employee employeeFromDb, Map<String, Object> requestMap) {
        Long id = employeeFromDb.getId();

        requestMap.forEach((key, value) -> ReflectionUtils.setFieldContent(employeeFromDb, key, value));

        employeeFromDb.setId(id);
        return employeeRepository.save(employeeFromDb);
    }
}
```