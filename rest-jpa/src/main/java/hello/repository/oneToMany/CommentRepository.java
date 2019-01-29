package hello.repository.oneToMany;

import hello.entity.oneToMany.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    Iterable<Comment> findAllByPostId(Long id);
}
