package hello.controller.oneToMany;

import hello.entity.oneToMany.Comment;
import hello.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Without postId in route, just "/comments"
 */
@RestController
@RequestMapping(CommentController.PATH)
public class CommentController {

    public final static String PATH = "/comments";

    @Autowired
    private CommentService service;

    @GetMapping("")
    public Iterable<Comment> getAll() {
        return service.getAll();
    }

    @PostMapping("")
    public Comment create(@RequestBody Comment comment) {
        return service.save(comment);
    }

    @GetMapping("/{id}")
    public Comment getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Comment update(@RequestBody Comment comment, @PathVariable Long id) {
        return service.update(id, comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
