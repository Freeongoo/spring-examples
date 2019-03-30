package hello.dao.oneToMany.impl;

import hello.dao.impl.AbstractBaseDao;
import hello.dao.oneToMany.CommentDao;
import hello.entity.oneToMany.Comment;
import hello.entity.oneToMany.Comment_;
import hello.entity.oneToMany.Post;
import hello.entity.oneToMany.Post_;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CommentDaoImpl extends AbstractBaseDao<Comment, Long> implements CommentDao {

    @Override
    protected Class<Comment> getPersistentClass() {
        return Comment.class;
    }

    @Override
    public List<Comment> findByPostId(Long postId) {
        CriteriaQuery<Comment> criteriaQuery = getCriteriaQuery();
        Root<Comment> root = getRoot(criteriaQuery);
        criteriaQuery.select(root);

        Join<Comment, Post> joinPost = root.join(Comment_.post);
        criteriaQuery.where(getCriteriaBuilder().equal(joinPost.get(Post_.id), postId));

        return getSession()
                .createQuery(criteriaQuery)
                .getResultList();
    }
}
