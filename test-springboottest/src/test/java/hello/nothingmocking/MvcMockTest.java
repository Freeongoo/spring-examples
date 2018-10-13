package hello.nothingmocking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import hello.controller.RestControllerEmployee;
import hello.model.Employee;
import hello.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@SqlGroup({
        @Sql("/db.sql"),
})
public class MvcMockTest {
    MockMvc mockMvc;

    @Autowired
    RestControllerEmployee controllerEmployee;

    @Autowired
    EmployeeService service;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.controllerEmployee).build();// Standalone context
    }

    @Test
    public void employee_WhenCheckStatus() throws Exception {
        Employee expectedEmployee = new Employee("Aha", "aha@mail.com");
        int id = service.insertWithReturnInsertedId(expectedEmployee);

        this.mockMvc.perform(get("/employees/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void employee_WhenCheckReturnJsonResult() throws Exception {
        String name = "Aha";
        String email = "aha@mail.com";
        Employee expectedEmployee = new Employee(name, email);

        int id = service.insertWithReturnInsertedId(expectedEmployee);

        this.mockMvc.perform(get("/employees/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is(name)))
                .andExpect(jsonPath("email", is(email)));
    }

    @Test
    public void employeeList() throws Exception {
        String name1 = "Aha";
        String email1 = "aha@mail.com";
        Employee expectedEmployee1 = new Employee(name1, email1);

        int id1 = service.insertWithReturnInsertedId(expectedEmployee1);

        String name2 = "Aha2";
        String email2 = "aha2@mail.com";
        Employee expectedEmployee2 = new Employee(name2, email2);

        int id2 = service.insertWithReturnInsertedId(expectedEmployee2);

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
        int expectedId = 1; // because first
        String name = "Aha";
        String email = "aha@mail.com";
        Employee expectedEmployee = new Employee(name, email);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(expectedEmployee);

        this.mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/employees/" + expectedId));
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