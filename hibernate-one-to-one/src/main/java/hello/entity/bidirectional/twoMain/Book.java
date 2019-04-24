package hello.entity.bidirectional.twoMain;

import hello.entity.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class Book extends AbstractBaseEntity {

    @OneToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Book() {
    }

    public Book(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
