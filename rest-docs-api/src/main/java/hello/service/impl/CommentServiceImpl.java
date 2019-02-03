package hello.service.impl;

import hello.entity.oneToMany.Comment;
import hello.entity.oneToMany.Post;
import hello.exception.ErrorCode;
import hello.exception.NotValidParamsException;
import hello.exception.ResourceNotFoundException;
import hello.repository.oneToMany.CommentRepository;
import hello.repository.oneToMany.PostRepository;
import hello.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class CommentServiceImpl extends AbstractService<Comment, Long> implements CommentService {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private PostRepository postRepository;

    @Override
    protected CrudRepository<Comment, Long> getRepository() {
        return repository;
    }

    @Override
    public Comment update(Long aLong, Comment entity) {
        validatePost(entity);
        return super.update(aLong, entity);
    }

    @Override
    public Comment save(Comment entity) {
        validatePost(entity);
        return super.save(entity);
    }

    private void validatePost(Comment entity) {
        if (isEmpty(entity.getPost())) {
            throw new NotValidParamsException("cannot passed post", ErrorCode.INVALID_PARAMS);
        }

        Long postId = entity.getPost().getId();
        Optional<Post> postFromDb = postRepository.findById(postId);
        postFromDb.orElseThrow(
                () -> new ResourceNotFoundException("cannot find post by id:" + postId, ErrorCode.OBJECT_NOT_FOUND));
    }
}
