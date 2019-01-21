package hello.repository.jsonReference;

import hello.entity.jsonReference.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Override
    List<Comment> findAll();
}
