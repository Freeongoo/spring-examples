package hello.mockBean.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import hello.entity.single.Employee;
import hello.exception.ResourceNotFoundException;
import hello.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static hello.controller.single.EmployeeController.PATH;
import static hello.exception.ErrorCode.OBJECT_NOT_FOUND;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@TestPropertySource(locations="/application-test.properties")
public class EmployeeControllerMockBeanTest {

    @MockBean
    private EmployeeService service;

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
    public void getAll_WhenEmpty_ShouldReturnEmptyList() throws Exception {
        when(service.getAll()).thenReturn(emptyList());

        this.mockMvc.perform(get(PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getAll_WhenExistOne_ShouldReturnList() throws Exception {
        Employee employee = new Employee("John", "admin");
        employee.setId(1L);
        when(service.getAll()).thenReturn(singletonList(employee));

        this.mockMvc.perform(get(PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("John")))
                .andExpect(jsonPath("$[0].role", is("admin")));
    }

    @Test
    public void create_ShouldReturnCreated() throws Exception {
        String name = "Aha";
        String role = "admin";
        Employee newEmployee = new Employee(name, role);

        Employee savedEmployee = new Employee(name, role);
        savedEmployee.setId(3L);

        when(service.save(any())).thenReturn(savedEmployee);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(newEmployee);

        this.mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", matchesPattern("http://localhost/employees/\\d+")));
    }

    @Test
    public void getById_WhenExist_ShouldReturnEmployee() throws Exception {
        Employee employee = new Employee("John", "admin");
        employee.setId(1L);
        when(service.getById(anyLong())).thenReturn(employee);

        this.mockMvc.perform(get(employeeRouteWithParam, 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("John")))
                .andExpect(jsonPath("role", is("admin")));
    }

    @Test
    public void getById_WhenNotExist_ShouldReturnNotFound() throws Exception {
        when(service.getById(anyLong()))
                .thenThrow(new ResourceNotFoundException("Cannot find entity by id", OBJECT_NOT_FOUND));

        this.mockMvc.perform(get(employeeRouteWithParam, -1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_ShouldReturnCreated() throws Exception {
        int id = 1;
        String name = "AhaHa";
        String role = "admin";
        Employee employee = new Employee(name, role);

        Employee updatedEmployee = new Employee(name, role);
        updatedEmployee.setId((long) id);
        when(service.update(anyLong(), any())).thenReturn(updatedEmployee);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(employee);

        this.mockMvc.perform(put(employeeRouteWithParam, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", matchesPattern("http://localhost/employees/\\d+")));
    }

    @Test
    public void update_WhenNotExist_ShouldReturnNotFound() throws Exception {
        int id = -1;
        String name = "AhaHa";
        String role = "admin";
        Employee expectedEmployee = new Employee(name, role);

        when(service.update(anyLong(), any()))
                .thenThrow(new ResourceNotFoundException("Cannot find entity by id", OBJECT_NOT_FOUND));

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
                .andExpect(status().isNoContent());
    }

    @Test
    public void remove_WhenNotExist_ShouldReturnNotFound() throws Exception {
        int notExistId = -1;

        doThrow(new ResourceNotFoundException("Cannot find entity by id", OBJECT_NOT_FOUND))
                .when(service)
                .delete(anyLong());

        this.mockMvc.perform(delete(employeeRouteWithParam, notExistId))
                .andExpect(status().isNotFound());
    }
}