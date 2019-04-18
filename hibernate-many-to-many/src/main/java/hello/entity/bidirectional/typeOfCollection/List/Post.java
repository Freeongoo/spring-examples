package hello.entity.bidirectional.typeOfCollection.List;

import hello.entity.AbstractBaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
public class Post extends AbstractBaseEntity<Long> {

    @ManyToMany
    @JoinTable(name = "post_comment",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "comment_id") })
    private List<Comment> comments = new ArrayList<>();

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
