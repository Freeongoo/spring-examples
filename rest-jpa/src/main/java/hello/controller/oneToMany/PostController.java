package hello.controller.oneToMany;

import hello.entity.oneToMany.Post;
import hello.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(PostController.PATH)
public class PostController {

    public final static String PATH = "/posts";

    @Autowired
    private PostService service;

    @GetMapping("")
    Iterable<Post> getAll() {
        return service.getAll();
    }

    @PostMapping("")
    ResponseEntity<Post> create(@RequestBody Post post, UriComponentsBuilder ucBuilder) {
        Post postSaved = service.save(post);

        HttpHeaders headers = getHttpHeaderWithLocation(ucBuilder, postSaved);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    Post getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<Post> update(@RequestBody Post post, @PathVariable Long id, UriComponentsBuilder ucBuilder) {
        Post postUpdated = service.update(id, post);

        HttpHeaders headers = getHttpHeaderWithLocation(ucBuilder, postUpdated);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private HttpHeaders getHttpHeaderWithLocation(UriComponentsBuilder ucBuilder, Post post) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path(PATH + "/{id}").buildAndExpand(post.getId()).toUri());
        return headers;
    }
}
