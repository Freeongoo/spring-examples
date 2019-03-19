# Validation API

https://habr.com/ru/post/424819/

Hibernate Validator is the reference implementation of the validation API.

## Configuration

For main and test dirs:
`cp application.properties.dist application.properties`

3.1. Validation API
Per the JSR 380 specification, the validation-api dependency contains the standard validation APIs:

### Add deps if not using Spring Boot

```
<dependency>
    <groupId>javax.validation</groupId>
    <artifactId>validation-api</artifactId>
    <version>2.0.0.Final</version>
</dependency>
```

#### Validation API Reference Implementation

Hibernate Validator is the reference implementation of the validation API.

To use it, we must add the following dependencies:

```
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>6.0.2.Final</version>
</dependency>
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator-annotation-processor</artifactId>
    <version>6.0.2.Final</version>
</dependency>
```

## Validation (JSR 303)

### Bean Validation POJO

See test: `/src/test/java/hello/beanValidation/PersonTest.java`

With custom annotation: `/src/test/java/hello/beanValidation/customAnnotation/PersonWithCustomAnnotationTest.java`

### Bean Validation with MVC Controller

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

#### Testing

For testing `@Valid` with response must run full application.  
See test: `/src/test/java/hello/controller/beanValidation/EmployeeRestTemplateTest.java`

When testing with @MockMvc check only status code, body - empty
See test: `/src/test/java/hello/controller/beanValidation/EmployeeControllerTest.java`

## Spring Validation

Validator: `/src/main/java/hello/springValidation/service/PeopleValidator.java`
Test: `/src/test/java/hello/springValidation/PeopleTest.java`

### Spring Validation for MVC controller

Example: `/src/main/java/hello/controller/springValidation/PeopleController.java`

1. Init binder out validator:
```
    @Autowired
    private PeopleValidator peopleValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(peopleValidator);
    }
```

2. Add in method check errors:

```
    @PostMapping("")
    public People create(@RequestBody @Valid People people, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new NotValidParamsException(bindingResult.getFieldError().getCode(), INVALID_PARAMS);
        }
        return service.save(people);
    }
```