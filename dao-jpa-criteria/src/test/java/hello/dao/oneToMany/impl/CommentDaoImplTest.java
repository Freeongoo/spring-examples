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
}