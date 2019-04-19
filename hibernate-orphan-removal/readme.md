# Learn Hibernate orphanRemove

You should set the orphanRemoval attribute of the @OneToMany association 
to true. And you also need to set the cascade attribute to 
CascadeType.PERSIST or CascadeType.ALL.

The point is that children, after the relation with the parent element has been removed, 
will be deleted.

## Configuration

For main and test dirs:
`cp application.properties.dist application.properties`

## Testing

### With `orphanRemove = false`

Test: `/src/test/java/hello/entity/withoutOrphanRemoval/AuthorTest.java`

### With `orphanRemove = true`

Test: `/src/test/java/hello/entity/orphanRemoval/PersonTest.java`

