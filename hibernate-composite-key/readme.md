# Learn Hibernate composite key

## Configuration

For main and test dirs:
`cp application.properties.dist application.properties`

## Main thoughts

* create separate class for id like "EntityId" with annotation `@Embeddable`
* include this class in our main `Entity` and annotation: `@EmbeddedId`

## Tests

### When composite key contains simple elementary fields

`/src/main/java/hello/entity/composite/simpleFields`

### When composite key contains relation fields

`/src/main/java/hello/entity/composite/relationFields`
