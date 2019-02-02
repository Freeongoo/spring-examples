package hello.service.jsonReference.impl;

import hello.entity.jsonReference.Post;
import hello.repository.jsonReference.PostRepository;
import hello.service.AbstractService;
import hello.service.jsonReference.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl extends AbstractService<Post, Long> implements PostService {

    private PostRepository repository;

    @Autowired
    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    protected CrudRepository<Post, Long> getRepository() {
        return repository;
    }
}
