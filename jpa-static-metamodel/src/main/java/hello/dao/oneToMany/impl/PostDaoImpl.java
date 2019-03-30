package hello.dao.oneToMany.impl;

import hello.dao.impl.AbstractBaseDao;
import hello.dao.oneToMany.PostDao;
import hello.entity.oneToMany.Post;
import org.springframework.stereotype.Repository;

@Repository
public class PostDaoImpl extends AbstractBaseDao<Post, Long> implements PostDao {

    @Override
    protected Class<Post> getPersistentClass() {
        return Post.class;
    }
}
