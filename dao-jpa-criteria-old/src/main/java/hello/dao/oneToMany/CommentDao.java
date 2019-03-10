package hello.dao.oneToMany;

import hello.dao.AbstractDao;
import hello.entity.oneToMany.Comment;

import java.util.List;

public interface CommentDao extends AbstractDao<Comment, Long> {

    public List<Comment> findByPostId(Long postId);
}
