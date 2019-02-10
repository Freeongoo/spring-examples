package hello.entity.nPlusOneProblem;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.BaseTest;
import hello.repository.nPlusOneProblem.PostRepository;
import hello.sqltracker.AssertSqlCount;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@DatabaseSetup({"/post.xml", "/comment.xml"})
public class NPlusOneProblemPostTest extends BaseTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void problemDetect() {
        List<Post> posts = postRepository.findAll();
        System.out.println("get all posts, not run select comments - Lazy");

        for (Post post: posts) {
            // since we have a lazy breeze
            // at each iteration when receiving the list - a separate "select" will be performed
            Set<Comment> comments = post.getComments();
            comments.forEach(c -> System.out.println(c.getName()));
        }

        AssertSqlCount.assertSelectCount(4);
    }
}
