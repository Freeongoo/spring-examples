# Find and fix some hibernate problems

## Configuration

For main and test dirs:
`cp application.properties.dist application.properties`

## N+1 issue

see problem: `/src/test/java/hello/entity/nPlusOneProblem/NPlusOneProblemPostTest.java`

### Solutions

#### Using batch fetching (simplest way)

##### Local in Entity

Using annotation: `@BatchSize(size=100)`

see solutions: `/src/test/java/hello/entity/nPlusOneProblem/solution/batchSize/ClientTest.java`

##### Global for all entities

add in application.properties:

```
spring.jpa.properties.hibernate.batch_fetch_style = PADDED
spring.jpa.properties.hibernate.default_batch_fetch_size = 25
```