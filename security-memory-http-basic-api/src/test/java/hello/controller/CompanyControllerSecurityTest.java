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
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CompanyControllerSecurityTest {

    private static final String API_COMPANY = "/api/company/";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private RequestPostProcessor httpBasicWithUser;
    private RequestPostProcessor httpBasicWithAdmin;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        httpBasicWithUser = httpBasic("user", "user");
        httpBasicWithAdmin = httpBasic("admin", "admin");
    }

    @Test
    public void getAll_WhenValidAdminUser() throws Exception {
        this.mockMvc.perform(get(API_COMPANY).with(httpBasicWithAdmin))
                .andExpect(status().isOk());
    }

    @Test
    public void getAll_WhenInvalidAdminUserPassword() throws Exception {
        this.mockMvc.perform(get(API_COMPANY).with(httpBasic("admin", "invalidPassword")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void create_WhenAdmin() throws Exception {
        String name = "Yahoo";
        Company company = new Company(name);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(company);

        this.mockMvc.perform(post(API_COMPANY).with(httpBasicWithAdmin)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void create_WhenUser() throws Exception {
        String name = "Yahoo";
        Company company = new Company(name);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(company);

        this.mockMvc.perform(post(API_COMPANY).with(httpBasicWithUser)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getById_WhenAdminUser() throws Exception {
        int id = 1;

        this.mockMvc.perform(get(API_COMPANY + id).with(httpBasicWithAdmin))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    public void getById_WhenUser() throws Exception {
        int id = 1;

        this.mockMvc.perform(get(API_COMPANY + id).with(httpBasicWithUser))
                .andExpect(status().isOk());
    }

    @Test
    public void delete_WhenAdminUser() throws Exception {
        int idForDelete = 1;

        this.mockMvc.perform(delete(API_COMPANY + idForDelete).with(httpBasicWithAdmin))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get(API_COMPANY).with(httpBasicWithAdmin))
                .andExpect(status().isOk());
    }

    @Test
    public void delete_WhenUser() throws Exception {
        int idForDelete = 1;

        this.mockMvc.perform(delete(API_COMPANY + idForDelete).with(httpBasicWithUser))
                .andExpect(status().isForbidden());
    }

    @Test
    public void update_WhenAdminUser() throws Exception {
        String name = "Yahoo";
        Company company = new Company(name);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(company);

        this.mockMvc.perform(patch(API_COMPANY + 1).with(httpBasicWithAdmin)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void update_WhenUser() throws Exception {
        String name = "Yahoo";
        Company company = new Company(name);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(company);

        this.mockMvc.perform(patch(API_COMPANY + 1).with(httpBasicWithUser)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isForbidden());
    }
}