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

Use `@JsonView` to filter fields depending on the context of serialization.
Only those fields that are marked `@JsonView` with this annotation will be serialized.

But we have a problem in that we have common entity fields that are in an abstract class: `AbstractEntity`
To solve this problem you need to use the following views classes:

- public interface CompanyViews {}
- public interface ProductViews {}
- public interface CommonViews {}
- public interface CompanyAndCommonViews extends CompanyViews, CommonViews {}
- public interface ProductAndCommonViews extends ProductViews, CommonViews {}

Entities:
- `/hello/entity/jsonView/Company.java`
- `/hello/entity/jsonView/Product.java`

Next add views classes to controllers.

In product controller:
```
@JsonView({CompanyAndCommonViews.List.class})
@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public @ResponseBody List<Company> getAll() {
    return service.getAll();
}
```

In company controller:
```
@JsonView({ProductAndCommonViews.List.class})
@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public @ResponseBody List<Company> getAll() {
    return service.getAll();
}
```

#### Result

/api/products

```
[{
  "id": 1,
  "name": "search engine",
  "company": {
    "id": 1,
    "name": "Google"
  }
}, {
  "id": 2,
  "name": "adv.",
  "company": {
    "id": 1,
    "name": "Google"
  }
}]
```

/api/companies

```
[{
  "id": 1,
  "name": "Google",
  "products": [{
    "id": 1,
    "name": "search engine"
  }, {
    "id": 2,
    "name": "adv."
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

## 4. Use @JsonIdentityInfo

Examples
- `/hello/entity/jsonIdentityInfo/Catalog.java`
- `/hello/entity/jsonIdentityInfo/Good.java`

#### Result

Catalog:
```
[{
  "id": 1,
  "name": "Catalog Name",
  "goods": [{
    "id": 2,
    "name": "Pumpkin",
    "catalog": 1
  }, {
    "id": 1,
    "name": "Apple",
    "catalog": 1
  }]
}]
```

Good:
```
[
    {
        "id": 1,
        "name": "Apple",
        "catalog":
        {
            "id": 1,
            "name": "Catalog Name",
            "goods": [1,
            {
                "id": 2,
                "name": "Pumpkin",
                "catalog": 1
            }]
        }
    },
    2
]
```

## 5. Using DTO

Add deps: 

```
<dependency>
    <groupId>org.modelmapper</groupId>
    <artifactId>modelmapper</artifactId>
    <version>2.3.2</version>
</dependency>
```

Create utils for convert Entity to DTO: `ObjectMapperUtils`

Next you need to create classes of the DTO, two ways:
1. If you just need to ignore some kind of entity field, then extend entity and just add `@JsonIgnoreProperties` see example: `TableStaffWithoutChairDto`
2. If you need to change the list of fields with the related object - then need to create a class (not extend from entity) with an associated object but another type see example `TableStaffDto`

And in the next - you need to either manually convert it into the DTO in the controller or use the abstract controller class like `AbstractDtoRestController`

#### Result

/api/chairs:
```
[{
  "id": 1,
  "name": "iChair#1",
  "tableStaff": {
    "id": 1,
    "name": "iTable"
  }
}, {
  "id": 2,
  "name": "iChair#2",
  "tableStaff": {
    "id": 1,
    "name": "iTable"
  }
}]
```

/api/table-staffs:
```
[{
  "id": 1,
  "name": "iTable",
  "chairs": [{
    "id": 2,
    "name": "iChair#2"
  }, {
    "id": 1,
    "name": "iChair#1"
  }]
}]
```


More examples: 
https://github.com/Freeongoo/spring-examples/tree/master/rest-jpa-dto-mapper

# Testing 

See controller tests