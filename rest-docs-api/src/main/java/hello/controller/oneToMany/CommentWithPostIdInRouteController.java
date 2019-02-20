package hello.controller.oneToMany;

import hello.entity.oneToMany.Comment;
import hello.service.CommentWithPostIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CommentWithPostIdInRouteController.PATH)
public class CommentWithPostIdInRouteController {

    public final static String PATH = "/posts/{postId}/comments";

    @Autowired
    private CommentWithPostIdService service;

    @GetMapping("")
    public Iterable<Comment> getAll(@PathVariable Long postId) {
        return service.getAll(postId);
    }

    @PostMapping("")
    public Comment create(@PathVariable Long postId, @RequestBody Comment comment) {
        return service.save(postId, comment);
    }

    @GetMapping("/{id}")
    public Comment getById(@PathVariable Long postId, @PathVariable Long id) {
        return service.getById(postId, id);
    }

    @PatchMapping("/{id}")
    public Comment update(@PathVariable Long postId, @RequestBody Comment comment, @PathVariable Long id) {
        return service.update(postId, id, comment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long postId, @PathVariable Long id) {
        service.delete(postId, id);
    }
}
