package hello.service;

import hello.model.Employee;

import java.util.List;

public interface EmployeeService {
    void insertEmployee(Employee emp);

    void insertEmployees(List<Employee> employees);

    void getAllEmployees();

    void getEmployeeById(String empid);
}
