package hello.controller;

import hello.entity.User;
import hello.service.UserCacheableService;
import hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(UserCacheableController.PATH)
public class UserCacheableController {

    public static final String PATH = "/api/cacheable/users";

    @Autowired
    private UserCacheableService service;

    @RequestMapping(value = "", method = GET, produces = APPLICATION_JSON_VALUE)
    public Collection<User> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public User getOne(@PathVariable Long id) {
        return service.getById(id);
    }
}
