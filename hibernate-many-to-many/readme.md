# Learn Hibernate @ManyToMany

## Configuration

For main and test dirs:
`cp application.properties.dist application.properties`

## Testing

### Bidirectional mappedBy (without any cascade)

`/src/main/java/hello/entity/bidirectional/mappedBy`

Where `User` is owner of relations.

It is important to understand that the relationship is created 
only when the list is assigned by the relationship owner (User)

### Bidirectional two entities are owners (without any cascade)

`/src/main/java/hello/entity/bidirectional/twoMain`

Where `Book` and `Author` is owners of relations.

it is important to understand that in this case, 
when each entity is the owner of the relationship, 
then to create a relation, it is enough to add collections to one of them. 
If you try to add relations at once in two entities, an exception will be thrown.