package hello.repository.jsonReference;

import hello.entity.jsonReference.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
