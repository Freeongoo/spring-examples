package hello.mockMvc.webApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.entity.oneToMany.Comment;
import hello.entity.oneToMany.Post;
import hello.repository.oneToMany.CommentRepository;
import hello.repository.oneToMany.PostRepository;
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
import java.util.Collections;
import java.util.Optional;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static hello.controller.oneToMany.PostController.PATH;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
public class PostControllerTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    private MockMvc mockMvc;
    private static String postRouteWithParam = PATH + "/{id}";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void getAll_WhenCheckOnlyStatus() throws Exception {
        this.mockMvc.perform(get(PATH))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAll() throws Exception {
        this.mockMvc.perform(get(PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Super news")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Some other news")));
    }

    @Test
    public void create() throws Exception {
        String name = "New News";
        Post post = new Post(name);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(post);

        this.mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", matchesPattern("http://localhost/posts/\\d+")));
    }

    @Test
    public void getById() throws Exception {
        int id = 1;

        this.mockMvc.perform(get(postRouteWithParam, id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("Super news")));
    }

    @Test
    public void getById_WhenNotExist() throws Exception {
        int idNotExist = -1;

        this.mockMvc.perform(get(postRouteWithParam, idNotExist))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update() throws Exception {
        int id = 1;
        String name = "Updated News";
        Post post = new Post(name);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(post);

        this.mockMvc.perform(put(postRouteWithParam, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", matchesPattern("http://localhost/posts/\\d+")));
    }

    @Test
    public void update_WhenTryChangeComment() throws Exception {
        int id = 1;
        String nameFroUpdate = "Updated News";
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setName("Trulala");     // try update name for comment
        Post post = new Post(nameFroUpdate);
        post.setComments(Collections.singletonList(comment));

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(post);

        this.mockMvc.perform(put(postRouteWithParam, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", matchesPattern("http://localhost/posts/\\d+")));

        // acutalization db
        em.flush();
        em.clear();

        // check updated post
        Optional<Post> optionalPost = postRepository.findById(1L);
        Post postFromDb = optionalPost.orElseThrow(() -> new RuntimeException("Not found"));
        assertThat(postFromDb.getName(), equalTo(nameFroUpdate));

        // check than not updated comment
        Optional<Comment> commentOptional = commentRepository.findById(1L);
        Comment commentFromDb = commentOptional.orElseThrow(() -> new RuntimeException("Not found"));
        assertThat(commentFromDb.getName(), is(not(equalTo("Trulala"))));
    }

    @Test
    public void update_WhenNotExist() throws Exception {
        int idNotExist = -1;
        String name = "Updated News";
        Post post = new Post(name);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(post);

        this.mockMvc.perform(put(postRouteWithParam, idNotExist)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test(expected = org.springframework.web.util.NestedServletException.class)
    public void delete_WhenNotDeleteRelatedElements_ShouldThrowException() throws Exception {
        int idForDelete = 1;

        this.mockMvc.perform(MockMvcRequestBuilders.delete(postRouteWithParam, idForDelete))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get(PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].name", is("Some other news")));
    }

    @Test
    public void delete_WhenDeleteAllRelation() throws Exception {
        int idForDelete = 1;

        // delete relation elements
        Optional<Post> optionalPost = postRepository.findById((long) idForDelete);
        Post post = optionalPost.orElseThrow(() -> new RuntimeException("Not found"));
        post.getComments().stream()
                .forEach(c -> commentRepository.delete(c));

        em.flush();
        em.clear();

        this.mockMvc.perform(MockMvcRequestBuilders.delete(postRouteWithParam, idForDelete))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get(PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].name", is("Some other news")));
    }
}