package examples.service;

import examples.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee getById(int id);

    List<Employee> getAll();

    int create(Employee employee);
}
