package hello.controller.oneToMany;

import hello.entity.oneToMany.Comment;
import hello.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import static hello.controller.Route.COMMENT_ROUTE;

@RestController
@RequestMapping(COMMENT_ROUTE)
public class CommentController {

    @Autowired
    private CommentService service;

    @GetMapping("")
    Iterable<Comment> getAll(@PathVariable Long postId) {
        return service.getAll(postId);
    }

    @PostMapping("")
    ResponseEntity<Comment> create(@PathVariable Long postId, @RequestBody Comment comment, UriComponentsBuilder ucBuilder) {
        Comment commentSaved = service.save(postId, comment);

        HttpHeaders headers = getHttpHeaderWithLocation(ucBuilder, postId, commentSaved);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    Comment getById(@PathVariable Long postId, @PathVariable Long id) {
        return service.getById(postId, id);
    }

    @PutMapping("/{id}")
    ResponseEntity<Comment> update(@PathVariable Long postId, @RequestBody Comment comment, @PathVariable Long id, UriComponentsBuilder ucBuilder) {
        Comment postUpdated = service.update(postId, id, comment);

        HttpHeaders headers = getHttpHeaderWithLocation(ucBuilder, postId, postUpdated);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long postId, @PathVariable Long id) {
        service.delete(postId, id);
        return ResponseEntity.noContent().build();
    }

    private HttpHeaders getHttpHeaderWithLocation(UriComponentsBuilder ucBuilder, Long postId, Comment comment) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path(COMMENT_ROUTE + "/{id}").buildAndExpand(postId, comment.getId()).toUri());
        return headers;
    }
}
