package hello.repository.jsonReference;

import hello.entity.jsonReference.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
