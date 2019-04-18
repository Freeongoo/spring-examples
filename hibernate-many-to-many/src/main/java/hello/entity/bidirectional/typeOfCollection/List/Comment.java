package hello.entity.bidirectional.typeOfCollection.List;

import hello.entity.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment")
public class Comment extends AbstractBaseEntity<Long> {

    @ManyToMany(mappedBy = "comments")
    private List<Post> posts = new ArrayList<>();

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
