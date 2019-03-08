package hello.business;


import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractIntegrationDBUnitTest;
import hello.persistence.entities.Author;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class AuthorsManagerTest extends AbstractIntegrationDBUnitTest {

    @Autowired
    private AuthorsManager authorsManager;

    @Test
    public void getAllAuthors_WithoutDBUnit() {
        List<Author> authors = authorsManager.getAllAuthors().collect(Collectors.toList());
        assertFalse(authors.isEmpty());
        assertEquals(5, authors.size());
    }

    @Test
    @DatabaseSetup(value = "/dbunit/books_authors.xml")
    public void getAllAuthors_WithDBUnit_DeleteAllAndInsertOnlyFromDBUnitFile() {
        List<Author> authors = authorsManager.getAllAuthors().collect(Collectors.toList());
        assertFalse(authors.isEmpty());
        assertEquals(1, authors.size());
    }

    @Test
    @DatabaseSetup(value = "/dbunit/books_authors.xml", type = DatabaseOperation.INSERT)
    public void getAllAuthors_WithDBUnit_Add() {
        List<Author> authors = authorsManager.getAllAuthors().collect(Collectors.toList());
        assertFalse(authors.isEmpty());
        assertEquals(6, authors.size());
    }
}