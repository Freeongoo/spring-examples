# Spring REST Mapping DTO

## Configuration

1. Copy `application.properties.dist` to `application.properties` 
2. Copy `liquibase.properties.dist` to `liquibase.properties`

And set correct connect to database

## What is the problem?

Suppose we have the following structure with dependent objects:

```
         1     ∞              1     ∞
Company <------- ProductType <------- Product 
```

And if we have all the data for relations, then when we serialize a company, we will get a serialization of all three objects `/api/companies`:

```
[
{
    "id": 1,
    "name": "Amazon",
    "productTypes": [
    {
        "id": 1,
        "name": "electronics",
        "products": [
        {
            "id": 1,
            "name": "iPhone X"
        },
        {
            "id": 2,
            "name": "Samsung Galaxy"
        }]
    },
    {
        "id": 2,
        "name": "household",
        "products": [
        {
            "id": 4,
            "name": "Sofa"
        },
        {
            "id": 3,
            "name": "Table"
        }]
    }]
}]
```

But we may have requirements so that, when displaying the list of `Company`, it is not detailed to the `Product`, but will stop at the `ProductType`

Mapping is used to solve this problem.

## Solutions

### Model Mapper

Install dependencies:

```
<dependency>
    <groupId>org.modelmapper</groupId>
    <artifactId>modelmapper</artifactId>
    <version>2.3.2</version>
</dependency>
```

Add in Spring Boot:

```
@Configuration
public class Config {
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
```

### How to create DTO for mapper?

1. Create separated class with same fields and setter, getter - and remove some fields that not need. Example: `CompanyDto`
2. For some reasons if need only ignore serialization some fields, you can only extend original Entity - and ignore field, example:

```
    @JsonIgnoreProperties("products")
    public class ProductTypeDto extends ProductType {
    
    }
```

#### Convert to DTO by facade

Do the conversion yourself.

- Create DTO: `CompanyDto`
- Create facade for convert Entity to DTO: `CompanyFacade`
- Use facade in controller: `CompanyMapperController`

#### Convert to DTO by annotation in controller

Using annotations for auto convert Entity to DTO

- Create DTO: `CompanyDto`
- Create annotation: `Dto`
- Create `ControllerAdvice`: `DtoMapperResponseBodyAdvice`
- Create controller for annotation: `CompanyMapperAnnotationController` extended from `AbstractMappingJacksonResponseBodyAdvice`
- Add in controller in method annotation with Dto for convert:
```
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Dto(CompanyDto.class)
    public @ResponseBody Company get(@PathVariable Long id) {
        return service.get(id);
    }
```

#### Convert to DTO by abstract controller with DTO as generic

- Create abstract controller `AbstractMapperController`
- Create realization company controller - by extend abstract `CompanyMapperFromAbstractController`

# Testing 

### DTO by facade

Result `/api/mapper/companies`:

```
[
{
    "id": 1,
    "name": "Amazon",
    "productTypes": [
    {
        "id": 1,
        "name": "electronics"
    },
    {
        "id": 2,
        "name": "household"
    }]
}]
```

### DTO by annotation in controller

Result `/api/mapper-annotation/companies`:

```
[
{
    "id": 1,
    "name": "Amazon",
    "productTypes": [
    {
        "id": 1,
        "name": "electronics"
    },
    {
        "id": 2,
        "name": "household"
    }]
}]
```


### DTO by abstract controller with DTO as generic

Result `/api/mapper-abstract/companies`:

```
[
{
    "id": 1,
    "name": "Amazon",
    "productTypes": [
    {
        "id": 1,
        "name": "electronics"
    },
    {
        "id": 2,
        "name": "household"
    }]
}]
```
