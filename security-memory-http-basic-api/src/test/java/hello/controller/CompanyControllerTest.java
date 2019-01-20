package hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import hello.entity.Company;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CompanyControllerTest {

    private static final String API_COMPANY = "/api/company/";

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
        this.mockMvc.perform(get(API_COMPANY))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Google")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Yandex")));
    }

    @Test
    public void create() throws Exception {
        String name = "Yahoo";
        Company company = new Company(name);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(company);

        this.mockMvc.perform(post(API_COMPANY)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)));
    }

    @Test
    public void getById() throws Exception {
        int id = 1;

        this.mockMvc.perform(get(API_COMPANY + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    public void getById_WhenNotExist() throws Exception {
        int idNotExist = -1;

        this.mockMvc.perform(get(API_COMPANY + idNotExist))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteById() throws Exception {
        int idForDelete = 1;

        this.mockMvc.perform(delete(API_COMPANY + idForDelete))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get(API_COMPANY))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void deleteById_WhenNotExist() throws Exception {
        int idForDelete = -1;

        this.mockMvc.perform(delete(API_COMPANY + idForDelete))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update() throws Exception {
        String name = "Yahoo";
        Company company = new Company(name);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(company);

        int companyId = 1;
        this.mockMvc.perform(patch(API_COMPANY + companyId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is(name)));
    }

    @Test
    public void update_WhenNotExistId() throws Exception {
        String name = "Yahoo";
        Company company = new Company(name);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(company);

        int companyId = -1;
        this.mockMvc.perform(patch(API_COMPANY + companyId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isNotFound());
    }
}