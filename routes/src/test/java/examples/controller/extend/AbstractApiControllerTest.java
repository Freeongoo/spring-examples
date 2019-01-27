package examples.controller.extend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AbstractApiController.class)
public class AbstractApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getMethod() throws Exception {
        this.mockMvc.perform(get("/companies"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getMethod_WhenNotExist_InheritanceOnAnnotationNotWork() throws Exception {
        this.mockMvc.perform(get("/api/companies"))
                .andDo(print()).andExpect(status().isNotFound());
    }
}