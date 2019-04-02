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