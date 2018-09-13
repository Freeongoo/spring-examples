package hello.dao;

import hello.model.Employee;

import java.util.List;

public interface EmployeeDao {
    void insert(Employee employee);

    int insertWithReturnInsertedId(Employee employee);

    void insertList(List<Employee> employees);

    List<Employee> getAll();

    Employee getById(int employeeId);

    Employee getByEmail(String email);

    void delete(Employee employee);
}