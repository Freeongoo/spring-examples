# Example jpa rest

## Configuration

For main and test dirs:
`cp application.properties.dist application.properties`

## Validation

### Bean Validation

Entity: `/src/main/java/hello/entity/beanValidation/Employee.java`

```
    @NotNull(message = "Please provide name")
    @Column(name = "name")
    private String name;
```

Add to controller `@Valid`:

```
    @PostMapping("")
    public Employee newEmployee(@RequestBody @Valid Employee newEmployee) {
        return service.save(newEmployee);
    }
```