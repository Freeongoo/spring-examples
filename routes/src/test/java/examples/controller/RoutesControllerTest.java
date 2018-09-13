package examples.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

// In this test, the full Spring application context is started, but without the server
// more faster

@RunWith(SpringRunner.class)
// We can narrow down the tests to just the web layer by using @WebMvcTest
@WebMvcTest(RoutesController.class)
public class RoutesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        this.mockMvc.perform(get("/hello"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello World")));
    }

    @Test
    public void simpleGet() throws Exception {
        this.mockMvc.perform(get("/simple-get"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("simple get - not params")));
    }
}