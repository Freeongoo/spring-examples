package hello.controller.beanValidation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.entity.beanValidation.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static hello.controller.beanValidation.EmployeeController.PATH;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
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
    public void getAll_ShouldReturnList() throws Exception {
        this.mockMvc.perform(get(PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("John")))
                .andExpect(jsonPath("$[0].role", is("admin")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Mike")))
                .andExpect(jsonPath("$[1].role", is("user")));
    }

    // TODO: not work, why?
    @Test
    public void create_WithoutName_ShouldThrowException() throws Exception {
        String role = "admin";
        Employee expectedEmployee = new Employee(null, role);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(expectedEmployee);

        ResultActions resultActions = this.mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print());

        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("content: " + contentAsString); // empty body

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("error", is("Bad Request")))
                .andExpect(jsonPath("timestamp", is(notNullValue())))
                .andExpect(jsonPath("status", is(400)))
                .andExpect(jsonPath("path", is(notNullValue())));

    }

    @Test
    public void create_ShouldReturnCreated() throws Exception {
        String name = "Aha";
        String role = "admin";
        Employee expectedEmployee = new Employee(name, role);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(expectedEmployee);

        this.mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getById_ShouldReturnEmployee() throws Exception {
        int id = 1;

        this.mockMvc.perform(get(employeeRouteWithParam, id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("John")))
                .andExpect(jsonPath("role", is("admin")));
    }

    @Test
    public void getById_WhenNotExist_ShouldReturnNotFound() throws Exception {
        int idNotExist = -1;

        this.mockMvc.perform(get(employeeRouteWithParam, idNotExist))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_ShouldReturnCreated() throws Exception {
        int id = 1;
        String name = "AhaHa";
        String role = "admin";
        Employee expectedEmployee = new Employee(name, role);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(expectedEmployee);

        this.mockMvc.perform(put(employeeRouteWithParam, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void update_WhenNotExist_ShouldReturnNotFound() throws Exception {
        int id = -1;
        String name = "AhaHa";
        String role = "admin";
        Employee expectedEmployee = new Employee(name, role);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(expectedEmployee);

        this.mockMvc.perform(put(employeeRouteWithParam, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void remove_ShouldReturnNotContent() throws Exception {
        int idForDelete = 1;

        this.mockMvc.perform(delete(employeeRouteWithParam, idForDelete))
                .andExpect(status().isOk());
    }

    @Test
    public void remove_WhenNotExist_ShouldReturnNotFound() throws Exception {
        int notExistId = -1;

        this.mockMvc.perform(delete(employeeRouteWithParam, notExistId))
                .andExpect(status().isNotFound());
    }
}