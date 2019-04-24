package hello.entity.bidirectional.twoMain;

import hello.entity.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "author")
public class Author extends AbstractBaseEntity {

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
