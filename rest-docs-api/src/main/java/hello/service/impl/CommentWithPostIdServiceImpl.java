package hello.service.impl;

import hello.entity.oneToMany.Comment;
import hello.entity.oneToMany.Post;
import hello.exception.NotValidParamsException;
import hello.exception.ResourceNotFoundException;
import hello.repository.oneToMany.CommentRepository;
import hello.repository.oneToMany.PostRepository;
import hello.service.CommentWithPostIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static hello.exception.ErrorCode.INVALID_PARAMS;
import static hello.exception.ErrorCode.OBJECT_NOT_FOUND;
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
    }

    @Override
    public Comment getById(Long postId, Long commentId) {
        validatePostId(postId);
        return getById(commentId);
    }

    @Override
    public Comment save(Long postId, Comment comment) {
        Optional<Post> postFromDb = postRepository.findById(postId);

        return postFromDb
                .map(post -> getSavedComment(comment, post))
                .orElseThrow(getNotFoundExceptionSupplier("Cannot create comment because passed not existing post id: " + postId, OBJECT_NOT_FOUND));
    }

    private Comment getSavedComment(Comment comment, Post postFromDb) {
        comment.setPost(postFromDb);
        return repository.save(comment);
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
        postFromDb.orElseThrow(getNotFoundExceptionSupplier("Passed post id not existing: " + postId, OBJECT_NOT_FOUND));
    }

    private void validateEqualsPostId(Long postId, Long commentId) {
        Optional<Comment> commentFromDb = repository.findById(commentId);

        Comment comment = commentFromDb
                .orElseThrow(getNotFoundExceptionSupplier("Not exist comment by id: " + commentId, OBJECT_NOT_FOUND));

        Post post = comment.getPost();
        if (isEmpty(post)) {
            throw new ResourceNotFoundException("Comment not exist Post", OBJECT_NOT_FOUND);
        }

        if (!postId.equals(post.getId())) {
            throw new NotValidParamsException("Try update comment from other post", INVALID_PARAMS);
        }
    }
}
