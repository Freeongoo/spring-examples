# Learn Hibernate @OneToOne

## Configuration

For main and test dirs:
`cp application.properties.dist application.properties`

## Testing

### Bidirectional mappedBy (without any cascade)

`/src/main/java/hello/entity/bidirectional/mappedBy`

Where `User` is owner of relations.

It is important to understand that the relationship is created 
only when set Role to User and save User.

### Bidirectional two entities are owners (without any cascade)

`/src/main/java/hello/entity/bidirectional/twoMain`

Where `Book` and `Author` is owners of relations.