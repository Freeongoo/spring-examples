# Spring Cache (@Cachable)

## Configuration

1. Copy `application.properties.dist` to `application.properties` 
1. Copy `application-test.properties.dist` to `application-test.properties` 

And set correct connect to database

## Test

### Without cache rest api

`/src/test/java/hello/controller/UserControllerWithoutCacheTest.java`

### With cache rest api

`/src/test/java/hello/controller/UserCacheableControllerTest.java`
