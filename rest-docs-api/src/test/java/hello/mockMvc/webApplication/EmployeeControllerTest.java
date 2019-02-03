package hello.mockMvc.webApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import hello.entity.single.Employee;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
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
import static hello.controller.single.EmployeeController.PATH;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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

    private static String employeeRouteWithParam = PATH + "/{id}";
    private static final String OUTPUT_DIRECTORY = "target/snippets";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation(OUTPUT_DIRECTORY);

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void getById() throws Exception {
        int id = 1;

        ResultActions resultActions = this.mockMvc
                .perform(RestDocumentationRequestBuilders.get(employeeRouteWithParam, id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("John")))
                .andExpect(jsonPath("role", is("admin")))
                .andDo(print());

        // added for rest docs !!!
        resultActions
                .andDo(document("employee/{method-name}",
                        pathParameters(
                                parameterWithName("id").description("id's in route")
                        ),
                        responseFields(
                                fieldWithPath("id").description("id"),
                                fieldWithPath("name").description("name"),
                                fieldWithPath("role").description("role")
                        )));
    }

    @Test
    public void getAll() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get(PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("John")))
                .andExpect(jsonPath("$[0].role", is("admin")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Mike")))
                .andExpect(jsonPath("$[1].role", is("user")))
                .andDo(print());

        // added for rest docs !!!
        resultActions
                .andDo(document("employee/{method-name}", responseFields(
                        fieldWithPath("[].id").description("id"),
                        fieldWithPath("[].name").description("name"),
                        fieldWithPath("[].role").description("role")
                )));
    }

    @Test
    public void create() throws Exception {
        String name = "Aha";
        String role = "admin";
        Employee expectedEmployee = new Employee(name, role);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(expectedEmployee);

        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.post(PATH)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", matchesPattern(".*/employees/\\d+")))
                .andDo(print());

        // added for rest docs !!!
        resultActions
                .andDo(document("employee/{method-name}",
                        requestFields(
                                fieldWithPath("name").description("name"),
                                fieldWithPath("role").description("role")
                        )));
    }

    @Test
    public void update() throws Exception {
        int id = 1;
        String name = "AhaHa";
        String role = "admin";
        Employee expectedEmployee = new Employee(name, role);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(expectedEmployee);

        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.put(employeeRouteWithParam, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", matchesPattern(".*/employees/\\d+")))
                .andDo(print());

        // added for rest docs !!!
        resultActions
                .andDo(document("employee/{method-name}",
                        pathParameters(
                                parameterWithName("id").description("id's in route")
                        ),
                        requestFields(
                                fieldWithPath("name").description("name"),
                                fieldWithPath("role").description("role")
                        )));
    }

    @Test
    public void remove() throws Exception {
        int idForDelete = 1;

        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.delete(employeeRouteWithParam, idForDelete))
                .andExpect(status().isNoContent());

        // added for rest docs !!!
        resultActions
                .andDo(document("employee/{method-name}",
                        pathParameters(
                                parameterWithName("id").description("id's in route")
                        )));
    }
}