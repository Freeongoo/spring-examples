package examples.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import examples.model.Employee;
import examples.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RestControllerEmployee.class)
public class RestControllerEmployeeTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService service;

    @Test
    public void employee_WhenCheckStatus() throws Exception {
        int id = 1;
        Employee expectedEmployee = new Employee(id, "Aha", "aha@mail.com");
        when(service.getById(id)).thenReturn(expectedEmployee);

        this.mockMvc.perform(get("/employees/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void employee_WhenCheckReturnJsonResult() throws Exception {
        int id = 1;
        String name = "Aha";
        String email = "aha@mail.com";
        Employee expectedEmployee = new Employee(id, name, email);

        when(service.getById(id)).thenReturn(expectedEmployee);

        this.mockMvc.perform(get("/employees/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is(name)))
                .andExpect(jsonPath("email", is(email)));
    }

    @Test
    public void employeeList() throws Exception {
        int id1 = 1;
        String name1 = "Aha";
        String email1 = "aha@mail.com";
        Employee expectedEmployee1 = new Employee(id1, name1, email1);

        int id2 = 2;
        String name2 = "Aha2";
        String email2 = "aha2@mail.com";
        Employee expectedEmployee2 = new Employee(id2, name2, email2);

        List<Employee> listEmployee = new ArrayList<>();
        listEmployee.add(expectedEmployee1);
        listEmployee.add(expectedEmployee2);

        when(service.getAll()).thenReturn(listEmployee);

        this.mockMvc.perform(get("/employees/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(id1)))
                .andExpect(jsonPath("$[0].name", is(name1)))
                .andExpect(jsonPath("$[0].email", is(email1)))
                .andExpect(jsonPath("$[1].id", is(id2)))
                .andExpect(jsonPath("$[1].name", is(name2)))
                .andExpect(jsonPath("$[1].email", is(email2)));
    }

    @Test
    public void createEmployee() throws Exception {
        int id = 1;
        String name = "Aha";
        String email = "aha@mail.com";
        Employee expectedEmployee = new Employee(id, name, email);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(expectedEmployee);

        when(service.create(any())).thenReturn(1);

        this.mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void createEmployee_WhenNotPassedData() throws Exception {
        this.mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void employee_WhenPassStringInParam() throws Exception {
        this.mockMvc.perform(get("/employees/myId"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void employee_WhenNotExistUrl() throws Exception {
        this.mockMvc.perform(get("/not-exist/"))
                .andExpect(status().isNotFound());
    }
}