package hello.service;

import hello.model.Employee;

import java.util.List;

public interface EmployeeService {

    void insert(Employee emp);

    int insertWithReturnInsertedId(Employee emp);

    void insertList(List<Employee> employees);

    List<Employee> getAll();

    Employee getById(int employeeId);

    Employee getByEmail(String email);

    void delete(Employee employee);
}
