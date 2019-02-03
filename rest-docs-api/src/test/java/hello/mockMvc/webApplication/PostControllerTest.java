package hello.mockMvc.webApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.entity.oneToMany.Post;
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
import static hello.controller.oneToMany.PostController.PATH;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
// DBUnit config:
@DatabaseSetup("/post.xml")
@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static String postRouteWithParam = PATH + "/{id}";

    @Test
    public void getById() throws Exception {
        int id = 1;

        this.mockMvc.perform(get(postRouteWithParam, id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("Super news")));
    }

    @Test
    public void getAll() throws Exception {
        this.mockMvc.perform(get(PATH))
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
                .andExpect(status().isCreated())
                .andExpect(header().string("location", matchesPattern("http://localhost/posts/\\d+")));
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
                .andExpect(status().isCreated())
                .andExpect(header().string("location", matchesPattern("http://localhost/posts/\\d+")));
    }

    @Test
    public void remove() throws Exception {
        int idForDelete = 1;

        this.mockMvc.perform(MockMvcRequestBuilders.delete(postRouteWithParam, idForDelete))
                .andExpect(status().isNoContent());
    }
}