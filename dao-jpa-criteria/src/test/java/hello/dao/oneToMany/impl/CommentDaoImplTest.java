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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@DatabaseSetup({"/post_comment_author.xml"})
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
    public void getByProps_WhenId() {
        Map<String, List<?>> props = new HashMap<>();
        props.put("id", singletonList(1L));
        List<Comment> comments = commentDao.getByProps(props);

        assertThat(comments.size(), equalTo(1));
    }

    @Test
    public void getByProps_WhenRelationId() {
        Map<String, List<?>> props = new HashMap<>();
        props.put("post.id", singletonList(1L));
        List<Comment> comments = commentDao.getByProps(props);

        assertThat(comments.size(), equalTo(2));
        for(Comment comment : comments) {
            assertThat(comment.getPost().getId(), equalTo(1L));
        }
    }

    @Test
    public void getByProps_WhenRelationIds() {
        Map<String, List<?>> props = new HashMap<>();
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        props.put("post.id", ids);
        List<Comment> comments = commentDao.getByProps(props);

        assertThat(comments.size(), equalTo(4));
    }

    @Test
    public void getByProps_WhenRelationId_WhenIdIsString() {
        Map<String, List<?>> props = new HashMap<>();
        props.put("post.id", singletonList("1"));
        List<Comment> comments = commentDao.getByProps(props);

        assertThat(comments.size(), equalTo(2));
        for(Comment comment : comments) {
            assertThat(comment.getPost().getId(), equalTo(1L));
        }
    }

    @Test
    public void getByProps_WhenRelationId_AndName() {
        Map<String, List<?>> props = new HashMap<>();
        props.put("post.id", singletonList(1L));
        props.put("name", singletonList("Comment#1"));
        List<Comment> comments = commentDao.getByProps(props);

        assertThat(comments.size(), equalTo(1));
        for(Comment comment : comments) {
            assertThat(comment.getPost().getId(), equalTo(1L));
        }
    }

    @Test
    public void getByProps_WhenRelationName() {
        Map<String, List<?>> props = new HashMap<>();
        props.put("post.name", singletonList("Post#1"));
        List<Comment> comments = commentDao.getByProps(props);

        assertThat(comments.size(), equalTo(2));
        for(Comment comment : comments) {
            assertThat(comment.getPost().getId(), equalTo(1L));
        }
    }

    @Test
    public void getByProps_WhenRelationNamePostAndAuthor() {
        Map<String, List<?>> props = new HashMap<>();
        props.put("post.name", singletonList("Post#1"));
        props.put("author.name", singletonList("Author#1"));
        List<Comment> comments = commentDao.getByProps(props);

        assertThat(comments.size(), equalTo(1));
        for(Comment comment : comments) {
            assertThat(comment.getPost().getId(), equalTo(1L));
            assertThat(comment.getAuthor().getId(), equalTo(1L));
        }
    }
}