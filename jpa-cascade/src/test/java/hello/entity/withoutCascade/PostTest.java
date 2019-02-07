package hello.entity.withoutCascade;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import hello.repository.withoutCascade.PostRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@DatabaseSetup("/withoutCascade/post_comment.xml")
public class PostTest extends AbstractJpaTest {

    @Autowired
    private PostRepository postRepository;

    @Test(expected = PersistenceException.class)
    public void removePost_WhenNotRemoveDependenceComments() {
        Post post = entityManager.find(Post.class, 1L);

        entityManager.remove(post);
        entityManager.flush();
    }

    @Test
    public void removePost_WhenBeforeRemoveDependenceComments() {
        Post post = entityManager.find(Post.class, 1L);

        post.getComments().forEach(c -> entityManager.remove(c));

        entityManager.remove(post);
        entityManager.flush();

        Post postAfterDelete = entityManager.find(Post.class, 1L);
        assertThat(postAfterDelete, equalTo(null));
    }
}