package hello.entity.bidirectional.twoMain;

import hello.entity.AbstractBaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "author")
public class Author extends AbstractBaseEntity<Long> {

    @ManyToMany
    @JoinTable(name = "author_book",
            joinColumns = { @JoinColumn(name = "author_id") },
            inverseJoinColumns = { @JoinColumn(name = "book_id") })
    private Set<Book> books = new HashSet<>();

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
