package hello.entity.nPlusOneProblem;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import hello.repository.nPlusOneProblem.PostRepository;
import hello.sqltracker.AssertSqlCount;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DatabaseSetup({"/post.xml", "/comment.xml"})
public class NPlusOneProblemPostTest extends AbstractJpaTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void problemDetect() {
        List<Post> posts = postRepository.findAll();
        System.out.println("get all posts, not run select comments - Lazy");

        for (Post post: posts) {
            // since we have a lazy breeze
            // at each iteration when receiving the list - a separate "select" will be performed
            List<Comment> comments = post.getComments();
            comments.forEach(c -> System.out.println(c.getName()));
        }

        AssertSqlCount.assertSelectCount(4);
    }
}
