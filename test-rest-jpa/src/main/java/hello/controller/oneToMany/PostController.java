package hello.controller.oneToMany;

import hello.entity.oneToMany.Post;
import hello.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PostController.PATH)
public class PostController {

    public final static String PATH = "/posts";

    @Autowired
    private PostService service;

    @GetMapping("")
    public Iterable<Post> getAll() {
        return service.getAll();
    }

    @PostMapping("")
    public Post create(@RequestBody Post post) {
        return service.save(post);
    }

    @GetMapping("/{id}")
    public Post getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Post update(@RequestBody Post post, @PathVariable Long id) {
        return service.update(id, post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
