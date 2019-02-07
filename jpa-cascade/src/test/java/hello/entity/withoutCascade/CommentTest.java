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
        entityManager.flush();

        Comment commentAfterDelete = entityManager.find(Comment.class, 1L);
        assertThat(commentAfterDelete, equalTo(null));

        Post post = entityManager.find(Post.class, 1L);
        assertThat(post.getComments().size(), equalTo(1));
    }
}