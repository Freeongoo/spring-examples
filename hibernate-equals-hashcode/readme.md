# How to override correctly equals/hashCode for jpa entity

## Configuration

For main and test dirs:
`cp application.properties.dist application.properties`

## Solutions

### Equals by all fields, hashCode by default

see: `/src/test/java/hello/entity/defaultHashCode/GoodTest.java`

### Natural id, equals by all fields, hashCode by only natural id

see: `/src/test/java/hello/entity/naturalId/BookTest.java`

### Equals by all fields, hashCode by surrogate id

see: `/src/test/java/hello/entity/overrideAllFields/UserTest.java`

### Equals by all fields, hashCode - constant

see: `/src/test/java/hello/entity/overrideAllFields_hashCodeConstant/ClientTest.java`