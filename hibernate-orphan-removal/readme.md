# Learn Hibernate orphanRemove

The point is that children, after the relation with the parent element has been removed, 
will be deleted when the option is set `orphanRemove = true`.

## Configuration

For main and test dirs:
`cp application.properties.dist application.properties`

## Testing

### With `orphanRemove = true`

Test: `/src/test/java/hello/entity/orphanRemoval/PersonTest.java`

