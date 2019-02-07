package hello.entity.withoutCascade;

import hello.entity.AbstractBaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment extends AbstractBaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment() {
    }

    public Comment(String name) {
        this.name = name;
    }

    public Comment(String name, Post post) {
        this.name = name;
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
