package hello.dao.oneToMany.impl;

import hello.dao.impl.AbstractBaseDao;
import hello.dao.oneToMany.CommentDao;
import hello.entity.oneToMany.Comment;
import hello.entity.oneToMany.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
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

        Join<Comment, Post> joinPost = root.join("post");
        Predicate predicate = getCriteriaBuilder().equal(joinPost.get("id"), postId);

        criteriaQuery
                .select(root)
                .where(predicate);

        return getSession()
                .createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public List<Comment> findNotInPassedIds(List<Long> ids) {
        CriteriaQuery<Comment> criteriaQuery = getCriteriaQuery();
        Root<Comment> root = getRoot(criteriaQuery);
        Path<Object> fieldId = root.get("id");
        Predicate in = fieldId.in(ids);
        Predicate predicate = getCriteriaBuilder().not(in);

        criteriaQuery
                .select(root)
                .where(predicate);

        return getSession()
                .createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public List<Comment> findLikeName(String partOfName) {
        CriteriaQuery<Comment> criteriaQuery = getCriteriaQuery();
        Root<Comment> root = getRoot(criteriaQuery);
        Path<String> fieldName = root.get("name");
        String s = "%" + partOfName + "%";
        Predicate predicate = getCriteriaBuilder().like(fieldName, s);

        criteriaQuery
                .select(root)
                .where(predicate);

        return getSession()
                .createQuery(criteriaQuery)
                .getResultList();
    }
}
