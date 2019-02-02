package hello.entity.oneToMany;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import static org.springframework.util.ObjectUtils.isEmpty;

@Entity
@Table(name = "comment")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment extends AbstractBaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
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

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("postId")
    public Post getPost() {
        return post;
    }

    @JsonProperty("post")
    public void setPost(Post post) {
        this.post = post;
    }

    // hack needed for api when passed "postId" in json param
    @Transient
    @JsonProperty("postId")
    public void setPostId(Long postId) {
        if (isEmpty(postId)) {
            return;
        }

        Post post = new Post();
        post.setId(postId);
        this.post = post;
    }
}
