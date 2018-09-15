package hello;

import hello.model.Comment;
import hello.model.Post;
import hello.repository.CommentRepository;
import hello.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationConsole implements CommandLineRunner {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConsole.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Clean up database tables
        commentRepository.deleteAllInBatch();
        postRepository.deleteAllInBatch();

        // Create a Post
        Post post = new Post("post title", "post description", "post content");

        // Create Comments
        Comment comment1 = new Comment("Great Post!");
        comment1.setPost(post);
        Comment comment2 = new Comment("Really helpful Post. Thanks a lot!");
        comment2.setPost(post);

        post.getComments().add(comment1);
        post.getComments().add(comment2);

        comment1.setPost(post);
        comment2.setPost(post);

        postRepository.save(post);
    }

}
