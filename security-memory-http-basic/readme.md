# Spring Security With "httpBasic"

Simple web app with html templates.

For demonstration use.

Save user in memory - used InMemoryUserDetailsManager 

## Testing 

1. With annotation @WithMockUser
2. Populating a Test User with a RequestPostProcessor with: `httpBasic("admin", "password")`

More example for testing: https://spring.io/blog/2014/05/23/preview-spring-security-test-web-security