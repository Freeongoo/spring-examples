package hello.controller.jsonReference;

import hello.entity.jsonReference.Post;
import hello.service.jsonReference.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PostController.PATH)
public class PostController {

    public static final String PATH = "/api/posts";

    private final PostService service;

    @Autowired
    public PostController(PostService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Post> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Post get(@PathVariable Long id) {
        return service.get(id);
    }
}
