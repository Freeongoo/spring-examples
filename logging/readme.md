# Logging

## Info

You can choose different levels of logging.  
List of levels from general to specific:
* TRACE 
* DEBUG 
* INFO 
* WARN
* ERROR
* FATAL
* OFF for disable

For example if you select level "WARN", then show only this levels:
* WARN
* ERROR
* FATAL

## Config in Spring Boot

Set config in file "application.properties"  

### Config level

For each individual package, you can set the logging level:
```
logging.level.org.springframework.web=ERROR
```
For spring packages show log only for level ERROR