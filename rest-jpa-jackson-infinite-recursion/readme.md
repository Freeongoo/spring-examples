# Json Infinite Recursion problem when working with Jackson

How to resolve Json Infinite Recursion problem when working with Jackson

## Configuration

1. Copy `application.properties.dist` to `application.properties` 
2. Copy `liquibase.properties.dist` to `liquibase.properties`

And set configuration params

# Solution

## 1. Use @JsonIgnore

The easiest option. 

For fields that we don’t want to serialize - add this annotation: `@JsonIgnore`

Can not preserve the Bidirectional-Relationships when deserialize string with relations

## 2. Use @JsonView

Examples:
- `/hello/entity/jsonView/Company.java`
- `/hello/entity/jsonView/Product.java`

Create class:

```
public class CompanyViews {

    public static class List {
    }

    public static class GetOne {
    }
}
```

Add this class in controller:

```
@JsonView({CompanyViews.List.class})
@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public @ResponseBody List<Company> getAll() {
    return service.getAll();
}

@JsonView({CompanyViews.GetOne.class})
@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public @ResponseBody Company get(@PathVariable Long id) {
    return service.get(id);
}
```

Further above each field, where we want it to be displayed during serialization, we add the class we need

In our case, we add all fields, except for the field of communication of the product with the company

In result of company `/api/company`:

```
[{
  "id": 1,
  "name": "Google",
  "products": [{
    "id": 2,
    "name": "adv."
  }, {
    "id": 1,
    "name": "search engine"
  }]
}]
```

The product’s link field is not displayed (`private Company company;`)

Can not preserve the Bidirectional-Relationships when deserialize string with relations

## 3. Use @JsonManagedReference, @JsonBackReference

- @JsonManagedReference: a part with the annotation will be serialized normally.
- @JsonBackReference: a part with the annotation will be omitted from serialization.

Examples:
- `/hello/entity/jsonReference/Comment.java`
- `/hello/entity/jsonReference/Post.java`

Can preserve the Bidirectional-Relationships when deserialize string with relations

# Testing 

See controller tests