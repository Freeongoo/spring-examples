package hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.entity.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static hello.controller.EmployeeController.PATH;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="/application-test.properties")
@Transactional
// DBUnit config:
@DatabaseSetup("/employee.xml")
@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class EmployeeControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private static String employeeRouteWithParam = PATH + "/{id}";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void getById() throws Exception {
        int id = 1;

        this.mockMvc.perform(get(employeeRouteWithParam, id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John")));
    }

    @Test
    public void getById_WhenNotExist() throws Exception {
        int idNotExist = -1;

        this.mockMvc.perform(get(employeeRouteWithParam, idNotExist))
                .andExpect(status().isNotFound());
    }

    @Test
    public void all() throws Exception {
        this.mockMvc.perform(get(PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("John")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Mike")));
    }

    @Test
    public void create() throws Exception {
        String name = "Aha";
        Employee employee = new Employee(name);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(employee);

        this.mockMvc.perform(post(PATH)
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Aha")));
    }

    @Test
    public void update() throws Exception {
        int id = 1;
        String name = "AhaHa";
        Employee employee = new Employee(name);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(employee);

        this.mockMvc.perform(put(employeeRouteWithParam, id)
                .contentType(APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("AhaHa")));
    }

    @Test
    public void remove() throws Exception {
        int idForDelete = 1;

        this.mockMvc.perform(delete(employeeRouteWithParam, idForDelete))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get(employeeRouteWithParam, idForDelete))
                .andExpect(status().isNotFound());
    }
}