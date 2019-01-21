package hello.service.jsonReference;

import hello.entity.jsonReference.Post;

import java.util.List;

public interface PostService {

    Post get(Long id);

    List<Post> getAll();
}
