package hello.entity.withoutCascade;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


@DatabaseSetup("/withoutCascade/post_comment.xml")
public class CommentTest extends AbstractJpaTest {

    @Test
    public void deleteComment() {
        Comment comment = entityManager.find(Comment.class, 1L);
        entityManager.remove(comment);

        flushAndClean();

        Comment commentAfterDelete = entityManager.find(Comment.class, 1L);
        assertThat(commentAfterDelete, equalTo(null));

        Post post = entityManager.find(Post.class, 1L);
        assertThat(post.getComments().size(), equalTo(1));
    }

    @Test
    public void setCommentNewPost() {
        Comment comment5 = entityManager.find(Comment.class, 5L); // without relation with Post
        Post post = entityManager.find(Post.class, 1L);

        // set parent
        comment5.setPost(post);

        entityManager.persist(comment5);

        flushAndClean();

        Post postAfterPersist = entityManager.find(Post.class, 1L);
        assertThat(postAfterPersist.getComments().size(), equalTo(3)); // added new
    }
}