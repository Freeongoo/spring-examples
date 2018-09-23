# Spring Security with remember me

For demonstration use.

Save user in MySQL with jdbc.

## Config

1. Create database
2. insert in db from file "sql.sql"
3. `cp application.properties.dist application.properties`
4. set dbName, login and password

## Info

In sql.sql password for two users - "password"  

Run util PasswordEncoderUtil.java for generate new password

## Remember me

Two way:
1. in cookie 
2. in db

See comments in WebSecurityConfig to see how it's work

#### How test

To easily see the remember me mechanism working, you can:
* log in with remember me active
* wait for the session to expire (or remove the JSESSIONID cookie in the browser)
* refresh the page  

Without remember me active, after the cookie expires the user should be redirected back to the login page. With remember me, the user now stays logged in with the help of the new token/cookie.