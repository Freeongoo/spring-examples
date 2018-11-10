package hello.business;

import hello.persistence.entities.Author;
import hello.persistence.repositories.AuthorRepository;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorsManager {

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorsManager(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Stream<Author> getAllAuthors() {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), true);
    }
}
