package hello.service.impl

import hello.exception.ResourceNotFoundException
import hello.repository.EmployeeRepository
import hello.service.EmployeeService
import spock.lang.Specification

class EmployeeServiceImplSpockTest extends Specification {
    EmployeeService employeeService
    EmployeeRepository employeeRepository

    def setup() {
        employeeRepository = Mock()
        employeeService = new EmployeeServiceImpl(employeeRepository)
    }

    def 'getById() when not exist employee should throw exception'() {
        given:
            _ * employeeRepository.findById(_ as Long) >> Optional.empty()

        when:
            employeeService.getById(123L)

        then:
            // simple way:
            //thrown ResourceNotFoundException

            // strict comparison
            def exception = thrown(ResourceNotFoundException)
            exception.message == 'Cannot find entity by id: 123'

            // only check start with
            assert exception.message.startsWith("Cannot find entity by id")
    }
}
