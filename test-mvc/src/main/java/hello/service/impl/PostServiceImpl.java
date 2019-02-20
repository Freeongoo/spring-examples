package hello.service.impl;

import hello.entity.oneToMany.Post;
import hello.repository.oneToMany.PostRepository;
import hello.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl extends AbstractService<Post, Long> implements PostService {

    @Autowired
    private PostRepository repository;

    @Override
    protected CrudRepository<Post, Long> getRepository() {
        return repository;
    }
}
