# Spring Security With "httpBasic" for REST API

For demonstration use.

Save user in memory - used InMemoryUserDetailsManager 

Other Entities in JDBC :)

## Configuration

1. Copy `application.properties.dist` to `application.properties` 
2. Copy `liquibase.properties.dist` to `liquibase.properties`

And set configuration params

## About Http Basic 

Basic authentication is a standard HTTP header with the user and password encoded in base64 : 
```
Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
```
The userName and password is encoded in the format `username:password`. This is one of the simplest technique to protect the REST resources because it does not require cookies. session identifiers or any login pages.


In case of basic authentication, the username and password is only encoded with Base64, but not encrypted or hashed in any way. Hence, it can be compromised by any man in the middle. Hence, it is always recommended to authenticate rest API calls by this header over a ssl connection.

## Permissions

In this implementation, we are not limited to roles ("ROLE_ADMIN" or "ROLE_USER").

Here we need a distinction at the level of rules (privileges). 

In particular, REST has the ability to read as well as write.

In this project create permissions for REST company:

```
public enum Permissions {

    COMPANY_VIEW("company#V"),      // GET
    COMPANY_EDIT("company#E"),      // PATCH or PUT
    COMPANY_CREATE("company#C");    // POST or DELETE

    private final String permissionString;

    private Permissions(String permissionString) {
        this.permissionString = permissionString;
    }

    public String getValue() {
        return permissionString;
    }

}
```

or of course you can create permissions to all CRUD operations, like this:

```
"company#C"
"company#R"
"company#U"
"company#D"
```

### Set in GrantedAuthority when create User

```
private UserDetails createAdmin(Function<String, String> encoder) {
    List<GrantedAuthority> adminGrantedAuthorities = new ArrayList<>();
    adminGrantedAuthorities.add(new SimpleGrantedAuthority(Permissions.COMPANY_VIEW.getValue()));
    adminGrantedAuthorities.add(new SimpleGrantedAuthority(Permissions.COMPANY_EDIT.getValue()));
    adminGrantedAuthorities.add(new SimpleGrantedAuthority(Permissions.COMPANY_CREATE.getValue()));

    return User.withUsername("admin")
            .passwordEncoder(encoder)
            .password("admin")
            .authorities(adminGrantedAuthorities)
            .build();
}
```

## Testing 

1. Testing integration test by controller without security: `CompanyControllerTest`
2. Testing permission with security: `CompanyControllerSecurityTest` (there is no need to test the returned data, it is enough to check only the status: allowed or prohibited)