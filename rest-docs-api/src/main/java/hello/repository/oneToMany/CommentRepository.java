package hello.repository.oneToMany;

import hello.entity.oneToMany.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    // not work, because customization Comment (add some hack fields)
    //Iterable<Comment> findAllByPostId(Long id);

    @Query("select c from Comment c join c.post p where (p.id = :postId)")
    Iterable<Comment> findAllByPostId(@Param("postId")Long postId);
}
