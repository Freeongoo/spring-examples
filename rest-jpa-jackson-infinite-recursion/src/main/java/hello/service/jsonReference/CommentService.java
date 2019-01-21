package hello.service.jsonReference;

import hello.entity.jsonReference.Comment;

import java.util.List;

public interface CommentService {

    Comment get(Long id);

    List<Comment> getAll();
}
