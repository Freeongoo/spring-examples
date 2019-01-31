package hello.controller.oneToMany;

import hello.entity.oneToMany.Comment;
import hello.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
    Iterable<Comment> getAll() {
        return service.getAll();
    }

    @PostMapping("")
    ResponseEntity<Comment> create(@RequestBody Comment comment, UriComponentsBuilder ucBuilder) {
        Comment commentSaved = service.save(comment);

        HttpHeaders headers = getHttpHeaderWithLocation(ucBuilder, commentSaved);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    Comment getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<Comment> update(@RequestBody Comment comment, @PathVariable Long id, UriComponentsBuilder ucBuilder) {
        Comment postUpdated = service.update(id, comment);

        HttpHeaders headers = getHttpHeaderWithLocation(ucBuilder, postUpdated);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private HttpHeaders getHttpHeaderWithLocation(UriComponentsBuilder ucBuilder, Comment comment) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path(PATH + "/{id}").buildAndExpand(comment.getId()).toUri());
        return headers;
    }
}
