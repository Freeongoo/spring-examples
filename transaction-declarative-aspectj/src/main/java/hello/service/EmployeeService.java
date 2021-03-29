package hello.service;

import hello.model.Employee;

import java.util.List;

public interface EmployeeService {

    void insertWithoutTransactional(Employee employee);

    void insertWithTransactional(Employee emp);

    void insertListWithoutTransactional(List<Employee> employees);

    void insertListWithTransactional(List<Employee> employees);

    void insertListWithoutTransactional_ButCallInsertListWithTransactional(List<Employee> employees);

    List<Employee> getAll();
}
