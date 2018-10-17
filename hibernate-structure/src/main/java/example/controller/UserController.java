package example.controller;

import example.entity.User;
import example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;

    @GetMapping("/users")
    Collection<User> all() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public User newUser(@RequestBody String body, UriComponentsBuilder ucBuilder) throws Exception {
        return userService.create(body);
    }

    // Single item

    /*@GetMapping("/users/{id}")
    User one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/users/{id}")
    ResponseEntity<User> replaceUser(@RequestBody User newUser, @PathVariable Long id, UriComponentsBuilder ucBuilder) {
        User updatedUser = repository.findById(id)
                .map(User -> {
                    User.setName(newUser.getName());
                    User.setRole(newUser.getRole());
                    return repository.save(User);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/Users/{id}").buildAndExpand(updatedUser.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }*/

}
