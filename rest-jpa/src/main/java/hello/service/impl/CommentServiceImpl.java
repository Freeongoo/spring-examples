package hello.service.impl;

import hello.entity.oneToMany.Comment;
import hello.repository.oneToMany.CommentRepository;
import hello.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends AbstractService<Comment, Long> implements CommentService {

    @Autowired
    private CommentRepository repository;

    @Override
    protected CrudRepository<Comment, Long> getRepository() {
        return repository;
    }
}
