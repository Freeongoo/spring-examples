# Rest Template

Accessing a third-party REST service inside a Spring application revolves around 
the use of the Spring `RestTemplate` class. 

The `RestTemplate` class is designed on the same principles as the many other Spring 
*Template classes (e.g., JdbcTemplate, JmsTemplate ), providing a simplified approach 
with default behaviors for performing complex tasks.

Given that the `RestTemplate` class is designed to call REST services, 
It should come as no surprise that its main methods are closely tied to 
REST’s underpinnings, which are the HTTP protocol’s methods: 
HEAD, GET, POST, PUT, DELETE, and OPTIONS.

## Base methods

#### HTTP GET Method

* getForObject(url, classType) – retrieve a representation by doing a GET on the URL. The response (if any) is unmarshalled to given class type and returned.
* getForEntity(url, responseType) – retrieve a representation as ResponseEntity by doing a GET on the URL.
* exchange(requestEntity, responseType) – execute the specified request and return the response as ResponseEntity.
* execute(url, httpMethod, requestCallback, responseExtractor) – execute the httpMethod to the given URI template, preparing the request with the RequestCallback, and reading the response with a ResponseExtractor.

#### HTTP POST Method

* postForObject(url, request, classType) – POSTs the given object to the URL, and returns the representation found in the response as given class type.
* postForEntity(url, request, responseType) – POSTs the given object to the URL, and returns the response as ResponseEntity.
* postForLocation(url, request, responseType) – POSTs the given object to the URL, and returns returns the value of the Location header.
* exchange(url, requestEntity, responseType)
* execute(url, httpMethod, requestCallback, responseExtractor)

#### HTTP PUT Method

* put(url, request) – PUTs the given request object to URL.

#### HTTP DELETE Method

* delete(url) – deletes the resource at the specified URL.

## Testing

#### Using RestTemplate

`/src/test/java/hello/RestTemplateTest.java`

#### Using TestRestTemplate

`/src/test/java/hello/TestRestTemplateTest.java`

#### Using real request and real remote API (bad idea)

`/src/test/java/hello/service/impl/UserServiceImplTest.java`

#### Using @RestClientTest for UserService

`/src/test/java/hello/service/impl/UserServiceImplMockRestTest.java`

For using @RestClientTest service must using `RestTemplateBuilder` and `RestTemplate`: 

```
@Service
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;

    public UserServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<User> getAll() {
        ResponseEntity<User[]> response = restTemplate.getForEntity("http://jsonplaceholder.typicode.com/posts", User[].class);
        User[] users = response.getBody();
        return asList(users);
    }

    @Override
    public User getById(Long id) {
        return restTemplate.getForObject("http://jsonplaceholder.typicode.com/posts/{id}", User.class, id);
    }
}
```