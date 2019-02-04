package hello.repository.nPlusOneProblem;

import hello.entity.nPlusOneProblem.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Iterable<Comment> findAllByPostId(Long id);
}
