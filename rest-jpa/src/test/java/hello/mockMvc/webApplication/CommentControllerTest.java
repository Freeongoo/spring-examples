package hello.mockMvc.webApplication;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractJpaTest;
import hello.entity.oneToMany.Comment;
import hello.repository.oneToMany.CommentRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static hello.controller.oneToMany.CommentController.PATH;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DatabaseSetup({"/post.xml", "/comment.xml"})
public class CommentControllerTest extends AbstractJpaTest {

    @Autowired
    private WebApplicationContext context;

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
                .andDo(print())
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

        String json = toJson(comment);

        this.mockMvc.perform(post(PATH, 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.postId", is((int)postId)));
    }

    @Test
    public void create_WhenNotPassedPostId() throws Exception {
        String name = "Comment new";
        Comment comment = new Comment(name);

        String json = toJson(comment);

        this.mockMvc.perform(post(PATH, 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getById() throws Exception {
        int id = 1;

        this.mockMvc.perform(get(commentRouteWithIdParam, id))
                .andDo(print())
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

        String json = toJson(comment);

        this.mockMvc.perform(put(commentRouteWithIdParam, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.postId", is((int)postId)));

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

        String json = toJson(comment);

        this.mockMvc.perform(put(commentRouteWithIdParam, idNotExist)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_WhenNotExistIdPost() throws Exception {
        int id = 1;
        long idNotExistPostId = -1;
        String name = "Comment For Update";
        Comment comment = new Comment(name);
        comment.setPostId(idNotExistPostId);

        String json = toJson(comment);

        this.mockMvc.perform(put(commentRouteWithIdParam, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void delete() throws Exception {
        int idForDelete = 1;

        this.mockMvc.perform(MockMvcRequestBuilders.delete(commentRouteWithIdParam, idForDelete))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get(PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    // TODO: add tests for check deleted dependencies entities
}