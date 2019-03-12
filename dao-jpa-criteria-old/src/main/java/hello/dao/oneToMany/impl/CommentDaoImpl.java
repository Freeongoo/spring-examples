package hello.dao.oneToMany.impl;

import hello.dao.impl.AbstractBaseDao;
import hello.dao.oneToMany.CommentDao;
import hello.entity.oneToMany.Comment;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDaoImpl extends AbstractBaseDao<Comment, Long> implements CommentDao {

    @Override
    protected Class<Comment> getPersistentClass() {
        return Comment.class;
    }

    @Override
    public List<Comment> findByPostId(Long postId) {
        return createEntityCriteria()
                .createAlias("post", "post")
                .add(Restrictions.eq("post.id", postId))
                .list();
    }
}
