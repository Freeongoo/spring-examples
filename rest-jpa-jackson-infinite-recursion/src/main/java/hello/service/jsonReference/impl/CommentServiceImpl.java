package hello.service.jsonReference.impl;

import hello.entity.jsonReference.Comment;
import hello.exception.ERROR_CODES;
import hello.exception.NotFoundException;
import hello.repository.jsonReference.CommentRepository;
import hello.service.jsonReference.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository repository;

    @Autowired
    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Comment get(Long id) {
        Optional<Comment> comment = repository.findById(id);
        return comment
                .orElseThrow(() -> new NotFoundException("Cannot find comment by id: " + id, ERROR_CODES.OBJECT_NOT_FOUND));
    }

    @Override
    public List<Comment> getAll() {
        return repository.findAll();
    }
}
