package hello.dao.oneToMany.impl;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.BaseTest;
import hello.container.FieldHolder;
import hello.container.OrderType;
import hello.container.QueryParams;
import hello.dao.oneToMany.CommentDao;
import hello.entity.oneToMany.Comment;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@DatabaseSetup({"/post_comment.xml"})
public class CommentDaoImplTest extends BaseTest {

    @Autowired
    private CommentDao commentDao;

    @Test
    public void findByPostId() {
        List<Comment> comments = commentDao.findByPostId(1L);

        assertThat(comments.size(), equalTo(2));
        for(Comment comment : comments) {
            assertThat(comment.getPost().getId(), equalTo(1L));
        }
    }

    @Test
    public void getByFields_WhenEmptyFields() {
        ArrayList<FieldHolder> fields = new ArrayList<>();
        List<Comment> comments = commentDao.getByFields(fields);

        assertThat(comments.size(), equalTo(0));
    }

    @Test(expected = NullPointerException.class)
    public void getByFields_WhenPassedNull() {
        List<Comment> comments = commentDao.getByFields(null);
    }

    @Test
    public void getByFields_ByName_WhenNotExist() {
        ArrayList<FieldHolder> fields = new ArrayList<>();
        fields.add(FieldHolder.of("name", "NotExistName", false));
        List<Comment> comments = commentDao.getByFields(fields);

        assertThat(comments.size(), equalTo(0));
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void getByFields_ByName_WhenNotExistFieldName() {
        ArrayList<FieldHolder> fields = new ArrayList<>();
        fields.add(FieldHolder.of("nameNotExist", "Comment#1", false));
        List<Comment> comments = commentDao.getByFields(fields);

        assertThat(comments.size(), equalTo(0));
    }

    @Test
    public void getByFields_ById() {
        ArrayList<FieldHolder> fields = new ArrayList<>();
        fields.add(FieldHolder.of("id", 1, false));
        List<Comment> comments = commentDao.getByFields(fields);

        assertThat(comments.size(), equalTo(1));
        assertThat(comments, containsInAnyOrder(
                hasProperty("id", is(1L))
        ));
    }

    @Test
    public void getByFields_ById_WheIdIsString() {
        ArrayList<FieldHolder> fields = new ArrayList<>();
        fields.add(FieldHolder.of("id", "1", false));
        List<Comment> comments = commentDao.getByFields(fields);

        assertThat(comments.size(), equalTo(1));
        assertThat(comments, containsInAnyOrder(
                hasProperty("id", is(1L))
        ));
    }

    @Test
    public void getByFields_ByNameAndPostId_WhenExist() {
        ArrayList<FieldHolder> fields = new ArrayList<>();
        fields.add(FieldHolder.of("name", "Comment#1", false));
        fields.add(FieldHolder.of("post", 1L, true));
        List<Comment> comments = commentDao.getByFields(fields);

        assertThat(comments.size(), equalTo(1));
        assertThat(comments, containsInAnyOrder(
                hasProperty("name", is("Comment#1"))
        ));
    }

    @Test
    public void getByProps_WhenRelationId() {
        Map<String, List<?>> props = new HashMap<>();
        props.put("post.id", Collections.singletonList(1L));
        List<Comment> comments = commentDao.getByProps(props);

        assertThat(comments.size(), equalTo(2));
        for(Comment comment : comments) {
            assertThat(comment.getPost().getId(), equalTo(1L));
        }
    }

    @Test
    public void getByProps_WhenRelationId_WhenIdIsString() {
        Map<String, List<?>> props = new HashMap<>();
        props.put("post.id", Collections.singletonList("1"));
        List<Comment> comments = commentDao.getByProps(props);

        assertThat(comments.size(), equalTo(2));
        for(Comment comment : comments) {
            assertThat(comment.getPost().getId(), equalTo(1L));
        }
    }

    @Test
    public void getByProps_WhenRelationId_AndName() {
        Map<String, List<?>> props = new HashMap<>();
        props.put("post.id", Collections.singletonList(1L));
        props.put("name", Collections.singletonList("Comment#1"));
        List<Comment> comments = commentDao.getByProps(props);

        assertThat(comments.size(), equalTo(1));
        for(Comment comment : comments) {
            assertThat(comment.getPost().getId(), equalTo(1L));
        }
    }

    @Test
    public void universalQuery_WhenEmptyFields_SortByName_LimitOne() {
        Map<String, List<?>> props = new HashMap<>();
        QueryParams queryParams = QueryParams.of("name", OrderType.DESC, 1, null);
        List<Comment> comments = commentDao.universalQuery(props, queryParams);

        assertThat(comments.size(), equalTo(1));
        for(Comment comment : comments) {
            assertThat(comment.getName(), equalTo("Comment#4"));
        }
    }

    @Test
    public void universalQuery_WhenEmptyFields_SortNullOrderNullLimit2() {
        Map<String, List<?>> props = new HashMap<>();
        QueryParams queryParams = QueryParams.of(null, null, 2, null);
        List<Comment> comments = commentDao.universalQuery(props, queryParams);

        assertThat(comments.size(), equalTo(2));
    }

    @Test
    public void universalQuery_WhenFieldByPostId_SortByName_LimitOne() {
        Map<String, List<?>> props = new HashMap<>();
        props.put("post.id", Collections.singletonList(1L));
        QueryParams queryParams = QueryParams.of("name", OrderType.DESC, 1, null);
        List<Comment> comments = commentDao.universalQuery(props, queryParams);

        assertThat(comments.size(), equalTo(1));
        for(Comment comment : comments) {
            assertThat(comment.getName(), equalTo("Comment#2"));
        }
    }

    @Test
    public void universalQuery_WhenFieldByPostId_SortById_LimitOne() {
        Map<String, List<?>> props = new HashMap<>();
        props.put("post.id", Collections.singletonList(1L));
        QueryParams queryParams = QueryParams.of("id", OrderType.DESC, 1, null);
        List<Comment> comments = commentDao.universalQuery(props, queryParams);

        assertThat(comments.size(), equalTo(1));
        for(Comment comment : comments) {
            assertThat(comment.getName(), equalTo("Comment#2"));
        }
    }

    @Test
    public void universalQuery_WhenFieldByPostId_SortById_WithoutOrderAndLimit() {
        Map<String, List<?>> props = new HashMap<>();
        props.put("post.id", Collections.singletonList(1L));
        QueryParams queryParams = QueryParams.of("id", null, null, null);
        List<Comment> comments = commentDao.universalQuery(props, queryParams);

        assertThat(comments.size(), equalTo(2));
        assertThat(comments.get(0).getId(), equalTo(1L));
    }

    @Test
    public void universalQuery_WhenFieldByPostId_SortById_WithoutOrder_WithLimitStart() {
        Map<String, List<?>> props = new HashMap<>();
        props.put("post.id", Collections.singletonList(1L));
        QueryParams queryParams = QueryParams.of("id", null, 1, 1);
        List<Comment> comments = commentDao.universalQuery(props, queryParams);

        assertThat(comments.size(), equalTo(1));
        assertThat(comments.get(0).getId(), equalTo(2L));
    }
}