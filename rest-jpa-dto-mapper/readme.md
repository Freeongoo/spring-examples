# Spring REST Mapper

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

And if we have all the data for relations, then when we serialize a company, we will get a serialization of all three objects:

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

# Testing 

