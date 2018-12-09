# Scheduler example

Example use @Scheduled for tasks  

All the scheduled methods should follow the following two criteria:  
* The method should have a void return type.
* The method should not accept any arguments.

## Testing

With lib Awaitility:

```
<dependency>
    <groupId>org.awaitility</groupId>
    <artifactId>awaitility</artifactId>
    <version>3.1.0</version>
    <scope>test</scope>
</dependency>
```