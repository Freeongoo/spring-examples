# Spring Boot 1.5 Security OAuth2: Authorization Server in memory

## Config

#### Basic Security

see `SecurityConfig.java`

* Set in memory token, method: `tokenStore`
* Config password generator, method: `encoder`
* Create in memory User, method: `userDetailsService`
* Set own in memory service, method: `globalUserDetails`

#### Authorization Server Config

see `AuthorizationServerConfig.java`

* Build with ClientDetailsServiceConfigurer token params
* Set own in memory token and set manager

#### Resource Server Config

see `ResourceServerConfig.java`

* need to demonstrate the use of the token. 
* set in `application.properties`: `security.oauth2.resource.filter-order=3`
* config access to "/secure/**" only for user with role "ADMIN"

## Testing

#### 1. Run application 
#### 2. Run in terminal:

```
curl -X POST \
  http://localhost:8080/oauth/token \
  -H 'authorization: Basic ZnJlZW9uZ29vLWNsaWVudDpmcmVlb25nb28tc2VjcmV0' \
  -F grant_type=password \
  -F username=admin \
  -F password=password
```

Example response:

```
{
    "access_token": "9241c64c-3f29-477d-a13f-e81b0456b0c6",
    "token_type": "bearer",
    "refresh_token": "a6cec0c2-911f-4cc7-a3bd-801b03f6dcca",
    "expires_in": 3599,
    "scope": "read write trust"
}
```

Where `ZnJlZW9uZ29vLWNsaWVudDpmcmVlb25nb28tc2VjcmV0` - encoded string in Base64.

This string is the encoding of two values: "CLIENT_ID:CLIENT_SECRET" (in our example: "freeongoo-client:freeongoo-secret")

##### How to generate base64 string for authorization? 
Go to site: "https://www.base64encode.org/" and encode string: "freeongoo-client:freeongoo-secret"

#### 3. Try get access to secret address: 
`http://localhost:8080/secret`

#### 4. Try again with generated token: 
`http://localhost:8080/secret?access_token=9241c64c-3f29-477d-a13f-e81b0456b0c6`