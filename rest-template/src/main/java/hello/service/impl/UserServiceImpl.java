package hello.service.impl;

import hello.entity.User;
import hello.service.UserService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Arrays.asList;

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
