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