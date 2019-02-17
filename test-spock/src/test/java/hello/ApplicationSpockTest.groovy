package hello

import hello.controller.EmployeeController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ApplicationSpockTest extends Specification {

    @Autowired(required = false)
    private EmployeeController employeeController

    def "when context is loaded then all expected beans are created"() {
        expect: "the EmployeeController is created"
        employeeController
    }
}
