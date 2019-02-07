package hello.entity.withoutCascade;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import org.junit.Test;

import javax.persistence.PersistenceException;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@DatabaseSetup("/withoutCascade/post_comment.xml")
public class PostTest extends AbstractJpaTest {

    @Test(expected = PersistenceException.class)
    public void removePost_WhenNotRemoveDependenceComments() {
        Post post = entityManager.find(Post.class, 1L);

        entityManager.remove(post);

        flushAndClean();
    }

    @Test
    public void removePost_WhenBeforeRemoveLinkDependenceComments() {
        Post post = entityManager.find(Post.class, 1L);

        post.getComments().stream()
                .peek(c -> c.setPost(null))
                .forEach(c -> entityManager.persist(c));

        entityManager.remove(post);

        flushAndClean();

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

        flushAndClean();

        Post postAfterDelete = entityManager.find(Post.class, 1L);
        assertThat(postAfterDelete, equalTo(null));

        Comment comment1 = entityManager.find(Comment.class, 1L);
        assertThat(comment1, equalTo(null));
    }

    @Test
    public void tryAddToPostNewAccount_WhenOnlySetInCollection() {
        Comment comment5 = entityManager.find(Comment.class, 5L); // without relation with Post
        Post post = entityManager.find(Post.class, 1L);

        List<Comment> comments = post.getComments();
        comments.add(comment5);

        post.getComments().clear();
        post.getComments().addAll(comments);

        entityManager.persist(post);

        flushAndClean();

        Post postAfterPersist = entityManager.find(Post.class, 1L);
        assertThat(postAfterPersist.getComments().size(), equalTo(2)); // nothing added
    }
}