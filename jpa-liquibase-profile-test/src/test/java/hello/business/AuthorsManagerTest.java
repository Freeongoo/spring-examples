package hello.business;


import hello.AbstractIntegrationTest;
import hello.persistence.entities.Author;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class AuthorsManagerTest extends AbstractIntegrationTest {

    @Autowired
    private AuthorsManager authorsManager;

    @Test
    public void getAllAuthors() {
        List<Author> authors = authorsManager.getAllAuthors().collect(Collectors.toList());
        assertFalse(authors.isEmpty());
        assertEquals(5, authors.size());
    }
}