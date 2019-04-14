package hello.business;


import hello.AbstractIntegrationTest;
import hello.persistence.entities.Book;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BooksManagerTest extends AbstractIntegrationTest {

    @Autowired
    private BooksManager booksManager;

    @Test
    public void getAllBooksReturnsDataFromDatabase() {
        List<Book> books = booksManager.getAllBooks().collect(Collectors.toList());
        assertFalse(books.isEmpty());
        assertEquals(5, books.size());
    }
}