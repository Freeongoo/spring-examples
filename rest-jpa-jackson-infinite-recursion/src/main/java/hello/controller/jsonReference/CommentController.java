package hello.controller.jsonReference;

import hello.entity.jsonReference.Comment;
import hello.service.jsonReference.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(CommentController.PATH)
public class CommentController {

    public static final String PATH = "/api/comments";

    private final CommentService service;

    @Autowired
    public CommentController(CommentService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Comment> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Comment get(@PathVariable Long id) {
        return service.getById(id);
    }
}
