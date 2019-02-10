package hello.mockMvc.webApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.entity.oneToMany.Comment;
import hello.repository.oneToMany.CommentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static hello.controller.oneToMany.CommentController.PATH;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
// DBUnit config:
@DatabaseSetup({"/post.xml", "/comment.xml"})
@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class CommentControllerTest {

    @Autowired
    private WebApplicationContext context;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CommentRepository commentRepository;

    private MockMvc mockMvc;
    private static String commentRouteWithIdParam = PATH + "/{id}";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void getAll_WhenCheckOnlyStatus() throws Exception {
        this.mockMvc.perform(get(PATH, 1))
                .andExpect(status().isOk());
    }

    @Test
    public void getAll() throws Exception {
        this.mockMvc.perform(get(PATH, 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(4)))
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
        long postId = 1;
        Comment comment = new Comment(name);
        comment.setPostId(postId);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(comment);

        this.mockMvc.perform(post(PATH, 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", matchesPattern("http://localhost/comments/\\d+")));
    }

    @Test
    public void create_WhenNotPassedPostId() throws Exception {
        String name = "Comment new";
        Comment comment = new Comment(name);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(comment);

        this.mockMvc.perform(post(PATH, 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getById() throws Exception {
        int id = 1;

        this.mockMvc.perform(get(commentRouteWithIdParam, id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("Comment#1")))
                .andExpect(jsonPath("postId", is(1)));
    }

    @Test
    public void getById_WhenNotExistCommentId() throws Exception {
        int idNotExist = -1;

        this.mockMvc.perform(get(commentRouteWithIdParam, idNotExist))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update() throws Exception {
        int id = 1;
        long postId = 1;
        String name = "Updated Comment";
        Comment comment = new Comment(name);
        comment.setPostId(postId);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(comment);

        this.mockMvc.perform(put(commentRouteWithIdParam, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", matchesPattern("http://localhost/comments/\\d+")));

        // additional check - not need
        em.flush();
        em.clear();

        Optional<Comment> optionalComment = commentRepository.findById(1L);
        Comment commentFromDb = optionalComment.orElseThrow(() -> new RuntimeException("cannot find"));
        assertThat(commentFromDb.getName(), equalTo("Updated Comment"));
    }

    @Test
    public void update_WhenNotExistIdComment() throws Exception {
        int idNotExist = -1;
        long postId = 1;
        String name = "Comment For Update";
        Comment comment = new Comment(name);
        comment.setPostId(postId);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(comment);

        this.mockMvc.perform(put(commentRouteWithIdParam, idNotExist)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_WhenNotExistIdPost() throws Exception {
        int id = 1;
        long idNotExistPostId = -1;
        String name = "Comment For Update";
        Comment comment = new Comment(name);
        comment.setPostId(idNotExistPostId);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(comment);

        this.mockMvc.perform(put(commentRouteWithIdParam, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void delete() throws Exception {
        int idForDelete = 1;

        this.mockMvc.perform(MockMvcRequestBuilders.delete(commentRouteWithIdParam, idForDelete))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get(PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    // TODO: add tests for check deleted dependencies entities
}