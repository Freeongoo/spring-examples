package hello.mockMvc.webApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.entity.oneToMany.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static hello.controller.oneToMany.CommentWithPostIdController.PATH;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
// DBUnit config:
@DatabaseSetup({"/post.xml", "/comment.xml"})
@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class CommentWithPostIdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static String commentRouteWithIdParam = PATH + "/{id}";

    @Test
    public void getAll_WhenCheckOnlyStatus() throws Exception {
        this.mockMvc.perform(get(PATH, 1))
                .andExpect(status().isOk());
    }

    @Test
    public void getById() throws Exception {
        int id = 1;
        int postId = 1;

        this.mockMvc.perform(get(commentRouteWithIdParam, postId, id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("Comment#1")))
                .andExpect(jsonPath("postId", is(1)));
    }

    @Test
    public void getAll() throws Exception {
        this.mockMvc.perform(get(PATH, 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Comment#1")))
                .andExpect(jsonPath("$[0].postId", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Comment#2")))
                .andExpect(jsonPath("$[1].postId", is(1)));
    }

    @Test
    public void create() throws Exception {
        String name = "Comment new";
        Comment comment = new Comment(name);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(comment);

        this.mockMvc.perform(post(PATH, 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", matchesPattern("http://localhost/posts/\\d+/comments/\\d+")));
    }

    @Test
    public void update() throws Exception {
        int id = 1;
        int postId = 1;
        String name = "Updated Comment";
        Comment comment = new Comment(name);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(comment);

        this.mockMvc.perform(put(commentRouteWithIdParam, postId, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", matchesPattern("http://localhost/posts/\\d+/comments/\\d+")));
    }

    @Test
    public void remove() throws Exception {
        int idForDelete = 1;
        int postId = 1;

        this.mockMvc.perform(MockMvcRequestBuilders.delete(commentRouteWithIdParam, postId, idForDelete))
                .andExpect(status().isNoContent());
    }
}