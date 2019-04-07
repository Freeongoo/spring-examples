package hello.entity.composite.relationFields;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class BookId implements Serializable {

    private static final long serialVersionUID = 5539858211987488108L;

    private String id;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public BookId(String id, Author author) {
        this.id = id;
        this.author = author;
    }

    public BookId() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
