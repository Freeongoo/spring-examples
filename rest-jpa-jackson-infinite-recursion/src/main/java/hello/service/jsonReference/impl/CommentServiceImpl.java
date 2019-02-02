package hello.service.jsonReference.impl;

import hello.entity.jsonReference.Comment;
import hello.repository.jsonReference.CommentRepository;
import hello.service.AbstractService;
import hello.service.jsonReference.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends AbstractService<Comment, Long> implements CommentService {

    private CommentRepository repository;

    @Autowired
    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    protected CrudRepository<Comment, Long> getRepository() {
        return repository;
    }
}
