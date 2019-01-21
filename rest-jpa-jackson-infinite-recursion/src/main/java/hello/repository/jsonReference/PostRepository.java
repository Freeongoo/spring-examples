package hello.repository.jsonReference;

import hello.entity.jsonReference.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    @Override
    List<Post> findAll();
}
