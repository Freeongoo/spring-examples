package hello.controller

import be.janbols.spock.extension.dbunit.DbUnit
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import javax.sql.DataSource

import static hello.controller.EmployeeController.PATH
import static org.hamcrest.Matchers.is
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@TestPropertySource(locations="/application-test.properties")
@Transactional
class EmployeeControllerSpockTest extends Specification {

    @Autowired
    private DataSource dataSource

    @Autowired
    private WebApplicationContext context

    private MockMvc mockMvc
    private static String employeeRouteWithParam = PATH + "/{id}"

    // insert some data in db
    @DbUnit(datasourceProvider = {
        dataSource
    })
    def content =  {
        Employee(id: 1, name: 'John')
        Employee(id: 2, name: 'Mike')
    }

    // like @Before
    def setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build()
    }

    def "when get by id existing employee"() {
        given:
            def id = 1

        when:
            def perform = this.mockMvc.perform(get(employeeRouteWithParam, id))

        then:
            perform
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                    .andDo(print())
                    .andExpect(jsonPath('$.id', is(1)))
                    .andExpect(jsonPath('$.name', is("John")))
    }

    def "when try get by id not existing employee"() {
        given:
            def idNotExist = -1

        when:
            def perform = this.mockMvc.perform(get(employeeRouteWithParam, idNotExist))

        then:
            perform
                    .andExpect(status().isNotFound())
    }
}