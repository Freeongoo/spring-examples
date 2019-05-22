package hello.controller.reflection.self;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.AbstractWebAppTest;
import hello.entity.Employee;
import hello.repository.EmployeeRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static hello.controller.reflection.self.EmployeeReflectionSelfController.PATH;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DatabaseSetup("/employee.xml")
public class EmployeeReflectionSelfControllerTest extends AbstractWebAppTest {

    private static String employeeRouteWithParam = PATH + "/{id}";

    @Autowired
    private EmployeeRepository employeeRepository;

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

    @Test
    public void update_WhenAllFieldsNull_WhenJsonWithoutNullFields_ShouldUpdateFieldsToNull() throws Exception {
        int id = 1;
        String name = "John";
        String role = "admin";

        Employee expectedEmployee = new Employee();

        String json = toJsonWithoutNullFields(expectedEmployee);    // json equal "{}"

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