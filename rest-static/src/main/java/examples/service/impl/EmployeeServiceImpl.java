package examples.service.impl;

import examples.model.Employee;
import examples.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// FAKE SERVICE
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public Employee getById(int id) {
        Employee employee = new Employee("Test", "test@test.com");
        employee.setId(id);
        return employee;
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "Test", "test@test.com"));
        employeeList.add(new Employee(2, "Test2", "test2@test.com"));

        return employeeList;
    }

    @Override
    public int create(Employee employee) {
        return 1;
    }
}
