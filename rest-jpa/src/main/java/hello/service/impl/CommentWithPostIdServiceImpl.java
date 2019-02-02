package hello.service.impl;

import hello.entity.oneToMany.Comment;
import hello.entity.oneToMany.Post;
import hello.exception.ErrorCode;
import hello.exception.NotValidParamsException;
import hello.exception.ResourceNotFoundException;
import hello.repository.oneToMany.CommentRepository;
import hello.repository.oneToMany.PostRepository;
import hello.service.CommentWithPostIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class CommentWithPostIdServiceImpl extends AbstractService<Comment, Long> implements CommentWithPostIdService {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private PostRepository postRepository;

    @Override
    protected CrudRepository<Comment, Long> getRepository() {
        return repository;
    }

    @Override
    public Iterable<Comment> getAll(Long postId) {
        return repository.findAllByPostId(postId);
        //return repository.findAllByPostId(postId);
    }

    @Override
    public Comment getById(Long postId, Long commentId) {
        validatePostId(postId);
        return getById(commentId);
    }

    @Override
    public Comment save(Long postId, Comment comment) {
        Optional<Post> postFromDb = postRepository.findById(postId);

        return postFromDb.map(p -> {
                comment.setPost(postFromDb.get());
                return repository.save(comment);
        }).orElseThrow(
                () -> new ResourceNotFoundException("Cannot create comment because passed not existing post id: " + postId, ErrorCode.OBJECT_NOT_FOUND)
        );
    }

    @Override
    public Comment update(Long postId, Long commentId, Comment comment) {
        validatePostId(postId);
        validateEqualsPostId(postId, commentId);

        return update(commentId, comment);
    }

    @Override
    public void delete(Long postId, Long commentId) {
        validatePostId(postId);
        delete(commentId);
    }

    @Override
    public void delete(Long postId, Comment comment) {
        validatePostId(postId);
        delete(comment);
    }

    private void validatePostId(Long postId) {
        Optional<Post> postFromDb = postRepository.findById(postId);
        postFromDb.orElseThrow(
                () -> new ResourceNotFoundException("Passed post id not existing: " + postId, ErrorCode.OBJECT_NOT_FOUND));
    }

    private void validateEqualsPostId(Long postId, Long commentId) {
        Optional<Comment> commentFromDb = repository.findById(commentId);

        if (!commentFromDb.isPresent()) {
            throw new ResourceNotFoundException("Not exist comment by id: " + commentId, ErrorCode.OBJECT_NOT_FOUND);
        }

        Comment comment = commentFromDb.get();
        Post post = comment.getPost();
        if (isEmpty(post)) {
            throw new ResourceNotFoundException("Comment not exist Post", ErrorCode.OBJECT_NOT_FOUND);
        }

        if (!postId.equals(post.getId())) {
            throw new NotValidParamsException("Try update comment from other post", ErrorCode.INVALID_PARAMS);
        }
    }
}
