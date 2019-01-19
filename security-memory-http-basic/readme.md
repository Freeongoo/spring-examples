# Spring Security With "httpBasic"

Simple web app with html templates.

For demonstration use.

Save user in memory - used InMemoryUserDetailsManager 

## Http Basic 

Basic authentication is a standard HTTP header with the user and password encoded in base64 : 
```
Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
```
The userName and password is encoded in the format `username:password`. This is one of the simplest technique to protect the REST resources because it does not require cookies. session identifiers or any login pages.


In case of basic authentication, the username and password is only encoded with Base64, but not encrypted or hashed in any way. Hence, it can be compromised by any man in the middle. Hence, it is always recommended to authenticate rest API calls by this header over a ssl connection.

## Testing 

1. With annotation @WithMockUser
2. Populating a Test User with a RequestPostProcessor with: `httpBasic("admin", "password")`

More example for testing: https://spring.io/blog/2014/05/23/preview-spring-security-test-web-security