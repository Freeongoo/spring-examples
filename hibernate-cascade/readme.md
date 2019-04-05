# Learn Hibernate Cascade Types

## Configuration

For main and test dirs:
`cp application.properties.dist application.properties`

## Testing

### Without Cascade Type - default mode

See tests: `/src/test/java/hello/entity/withoutCascade`

### Without Cascade Type - remove

See tests: `/src/test/java/hello/entity/removeCascade`
See tests with orphan: `/src/test/java/hello/entity/removeCascade/orphan`

### With all cascade @ManyToMany

`/src/test/java/hello/entity/allCascade/manyToMany`

### Without Cascade Type - persist

See tests: `/src/test/java/hello/entity/persistCascade`
See tests with orphan: `/src/test/java/hello/entity/persistCascade/orphan`

ManyToMany: `/src/main/java/hello/entity/withoutCascade/manyToMany`
