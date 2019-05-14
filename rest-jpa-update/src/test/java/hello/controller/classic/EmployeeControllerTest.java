package hello.controller.classic;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractWebAppTest;
import hello.entity.Employee;
import hello.repository.EmployeeRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static hello.controller.classic.EmployeeController.PATH;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DatabaseSetup("/employee.xml")
public class EmployeeControllerTest extends AbstractWebAppTest {

    private static String employeeRouteWithParam = PATH + "/{id}";

    @Autowired
    private EmployeeRepository employeeRepository;

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

    @Test
    public void create_ShouldReturnOk() throws Exception {
        String name = "Aha";
        String role = "admin";
        Employee expectedEmployee = new Employee(name, role);

        String json = toJson(expectedEmployee);

        this.mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.role", is(role)));
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
    public void remove_ShouldReturnOk() throws Exception {
        int idForDelete = 1;

        this.mockMvc.perform(delete(employeeRouteWithParam, idForDelete))
                .andExpect(status().isOk());

        this.mockMvc.perform(get(PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].name", is("Mike")))
                .andExpect(jsonPath("$[0].role", is("user")));
    }

    @Test
    public void remove_WhenNotExist_ShouldReturnNotFound() throws Exception {
        int notExistId = -1;

        this.mockMvc.perform(delete(employeeRouteWithParam, notExistId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_ShouldReturnOk() throws Exception {
        int id = 1;
        String name = "AhaHa";
        String role = "admin";
        Employee expectedEmployee = new Employee(name, role);

        String json = toJson(expectedEmployee);

        this.mockMvc.perform(patch(employeeRouteWithParam, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.role", is(role)));

        flushAndClean();

        Employee employeeUpdated = employeeRepository.findById((long) id).get();
        assertThat(employeeUpdated.getName(), equalTo(name));
        assertThat(employeeUpdated.getRole(), equalTo(role));
    }

    @Test
    public void update_WhenAllFieldsNull_WhenJsonWithNullFields_ShouldUpdateFieldsToNull() throws Exception {
        int id = 1;
        Employee expectedEmployee = new Employee();

        String json = toJson(expectedEmployee);

        this.mockMvc.perform(patch(employeeRouteWithParam, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", isEmptyOrNullString()))
                .andExpect(jsonPath("$.role", isEmptyOrNullString()));

        flushAndClean();

        Employee employeeUpdated = employeeRepository.findById((long) id).get();
        assertThat(employeeUpdated.getName(), equalTo(null));
        assertThat(employeeUpdated.getRole(), equalTo(null));
    }

    // it's not obvious, but it's standard
    @Test
    public void update_WhenAllFieldsNull_WhenJsonWithoutNullFields_ShouldUpdateFieldsToNull() throws Exception {
        int id = 1;
        Employee expectedEmployee = new Employee();

        String json = toJsonWithoutNullFields(expectedEmployee);    // json equal "{}"

        this.mockMvc.perform(patch(employeeRouteWithParam, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", isEmptyOrNullString()))
                .andExpect(jsonPath("$.role", isEmptyOrNullString()));

        flushAndClean();

        Employee employeeUpdated = employeeRepository.findById((long) id).get();
        assertThat(employeeUpdated.getName(), equalTo(null));
        assertThat(employeeUpdated.getRole(), equalTo(null));
    }

    @Test
    public void update_WhenNotExist_ShouldReturnNotFound() throws Exception {
        int id = -1;
        String name = "AhaHa";
        String role = "admin";
        Employee expectedEmployee = new Employee(name, role);

        String json = toJson(expectedEmployee);

        this.mockMvc.perform(patch(employeeRouteWithParam, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}