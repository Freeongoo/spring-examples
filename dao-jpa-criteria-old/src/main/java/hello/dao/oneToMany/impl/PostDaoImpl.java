package hello.dao.oneToMany.impl;

import hello.dao.impl.AbstractDaoImpl;
import hello.dao.oneToMany.PostDao;
import hello.entity.oneToMany.Post;
import org.springframework.stereotype.Repository;

@Repository
public class PostDaoImpl extends AbstractDaoImpl<Post, Long> implements PostDao {

    @Override
    protected Class<Post> getPersistentClass() {
        return Post.class;
    }
}
