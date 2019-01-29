package hello.service;

import hello.entity.oneToMany.Comment;

public interface CommentService extends Service<Comment, Long> {

    Iterable<Comment> getAll(Long postId);

    Comment getById(Long postId, Long commentId);

    Comment save(Long postId, Comment comment);

    Comment update(Long postId, Long commentId, Comment comment);

    void delete(Long postId, Long commentId);

    void delete(Long postId, Comment comment);
}
