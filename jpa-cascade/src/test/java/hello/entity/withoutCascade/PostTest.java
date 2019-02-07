package hello.entity.withoutCascade;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import org.junit.Test;

import javax.persistence.PersistenceException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@DatabaseSetup("/withoutCascade/post_comment.xml")
public class PostTest extends AbstractJpaTest {

    @Test(expected = PersistenceException.class)
    public void removePost_WhenNotRemoveDependenceComments() {
        Post post = entityManager.find(Post.class, 1L);

        entityManager.remove(post);
        entityManager.flush();
    }

    @Test
    public void removePost_WhenBeforeRemoveLinkDependenceComments() {
        Post post = entityManager.find(Post.class, 1L);

        post.getComments().stream()
                .peek(c -> c.setPost(null))
                .forEach(c -> entityManager.persist(c));

        entityManager.remove(post);
        entityManager.flush();

        Post postAfterDelete = entityManager.find(Post.class, 1L);
        assertThat(postAfterDelete, equalTo(null));

        Comment comment1 = entityManager.find(Comment.class, 1L);
        assertThat(comment1.getPost(), equalTo(null));
    }

    @Test
    public void removePost_WhenBeforeRemoveDependenceComments() {
        Post post = entityManager.find(Post.class, 1L);

        post.getComments().forEach(c -> entityManager.remove(c));

        entityManager.remove(post);
        entityManager.flush();

        Post postAfterDelete = entityManager.find(Post.class, 1L);
        assertThat(postAfterDelete, equalTo(null));

        Comment comment1 = entityManager.find(Comment.class, 1L);
        assertThat(comment1, equalTo(null));
    }
}