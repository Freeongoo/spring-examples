package hello.service.jsonReference.impl;

import hello.entity.jsonReference.Post;
import hello.exception.ERROR_CODES;
import hello.exception.NotFoundException;
import hello.repository.jsonReference.PostRepository;
import hello.service.jsonReference.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository repository;

    @Autowired
    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public Post get(Long id) {
        Optional<Post> post = repository.findById(id);
        return post
                .orElseThrow(() -> new NotFoundException("Cannot find post by id: " + id, ERROR_CODES.OBJECT_NOT_FOUND));
    }

    @Override
    public List<Post> getAll() {
        return repository.findAll();
    }
}
