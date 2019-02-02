package hello.controller.jsonView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static hello.controller.jsonView.CompanyController.PATH;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CompanyControllerTest {

    private static final String PATH_WITH_ID = PATH + "/{id}";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void getAll() throws Exception {
        this.mockMvc.perform(get(PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Google")))
                .andExpect(jsonPath("$[0].products", hasSize(2)))
                .andExpect(jsonPath("$[0].products[*].id", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[0].products[*].name", containsInAnyOrder("search engine", "adv.")))
                .andExpect(jsonPath("$[0].products[*].company").doesNotExist());
    }

    @Test
    public void getById() throws Exception {
        int id = 1;

        this.mockMvc.perform(get(PATH_WITH_ID, id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("$.name", is("Google")))
                .andExpect(jsonPath("$.products", hasSize(2)))
                .andExpect(jsonPath("$.products[*].id", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$.products[*].name", containsInAnyOrder("search engine", "adv.")))
                .andExpect(jsonPath("$.products[*].company").doesNotExist());
    }

    @Test
    public void getById_WhenNotExist() throws Exception {
        int idNotExist = -1;

        this.mockMvc.perform(get(PATH_WITH_ID, idNotExist))
                .andExpect(status().isNotFound());
    }
}