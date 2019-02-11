# Example jpa rest for store json to database and return json 

Sometimes there is a need to save JSON to the database and return the result as JSON and not as a string

## Configuration

For main and test dirs:
`cp application.properties.dist application.properties`

## 1. Save json by custom field annotation

Code in dir: `/src/main/java/hello/json`

See example use: `/src/main/java/hello/entity/customField/Employee.java`

### Test

see: `/src/test/java/hello/controller/customField/EmployeeControllerTest.java`
