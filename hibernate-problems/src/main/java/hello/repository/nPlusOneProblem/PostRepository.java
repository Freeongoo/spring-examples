package hello.repository.nPlusOneProblem;

import hello.entity.nPlusOneProblem.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
