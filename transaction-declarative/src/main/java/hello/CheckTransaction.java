package hello;

import hello.model.Employee;
import hello.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CheckTransaction {
    private final EmployeeService employeeService;

    @Autowired
    public CheckTransaction(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Transactional
    public void correctCreateNewEmployee() {
        employeeService.insert(new Employee(100,"Free", "test@test.com"));
    }

    @Transactional
    public void insertSameEmails() {
        employeeService.insert(new Employee("Free", "test@test.com"));
        employeeService.insert(new Employee("Free2", "test@test.com"));
    }
}
