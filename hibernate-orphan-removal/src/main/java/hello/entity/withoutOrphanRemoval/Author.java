package hello.entity.withoutOrphanRemoval;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "author")
public class Author {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        books.add( book );
        book.setAuthor( this );
    }

    public void removeBook(Book book) {
        books.remove( book );
        book.setAuthor( null );
    }
}
