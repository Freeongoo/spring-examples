package hello.dao.oneToMany;

import hello.dao.BaseDao;
import hello.entity.oneToMany.Comment;

import java.util.List;

public interface CommentDao extends BaseDao<Comment, Long> {

    public List<Comment> findByPostId(Long postId);
}
