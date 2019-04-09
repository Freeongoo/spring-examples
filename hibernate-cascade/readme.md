# Learn Hibernate Cascade Types

## Configuration

For main and test dirs:
`cp application.properties.dist application.properties`

## Testing

### Without Cascade Type - default mode

See tests: `/src/test/java/hello/entity/withoutCascade`

ManyToMany: `/src/main/java/hello/entity/withoutCascade/manyToMany`

### With Cascade Type - remove

See tests: `/src/test/java/hello/entity/removeCascade`

### With Cascade Type - persist

See tests: `/src/test/java/hello/entity/persistCascade`

### With Cascade Type - all

ManyToMany: `/src/test/java/hello/entity/allCascade/manyToMany`
