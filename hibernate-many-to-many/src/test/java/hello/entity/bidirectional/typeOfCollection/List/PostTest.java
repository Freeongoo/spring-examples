package hello.entity.bidirectional.typeOfCollection.List;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import hello.sqltracker.AssertSqlCount;
import org.junit.Test;

@DatabaseSetup("/post_comment.xml")
public class PostTest extends AbstractJpaTest {

    @Test
    public void deleteOneCommentFromPost() {
        Post post = entityManager.find(Post.class, 1L);
        Comment comment = entityManager.find(Comment.class, 1L);
        post.getComments().remove(comment);

        flushAndClean();

        AssertSqlCount.assertSelectCount(3);
        AssertSqlCount.assertDeleteCount(1);
        AssertSqlCount.assertInsertCount(1);    // request not needed, executed because collection type List (use Set)
        AssertSqlCount.assertUpdateCount(0);
    }
}