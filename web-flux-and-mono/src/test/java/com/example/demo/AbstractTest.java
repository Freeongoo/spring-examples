package com.example.demo;

import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractTest {

    protected User peter = new User("Peter", "Griffin");
    protected User lois = new User("Lois", "Griffin");
    protected User brain = new User("Brain", "Griffin");

    @BeforeEach
    void setUp() {

    }
}
