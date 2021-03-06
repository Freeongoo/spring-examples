package hello.service.transaction.impl;

import hello.dao.EmployeeDao;
import hello.model.Employee;
import hello.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * {@link #insertListWithoutTransactional_ButCallInsertListWithTransactional} not call transactional
 */
@Service
public class EmployeeServiceWithTransactional implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public void insertWithoutTransactional(Employee employee) {
        employeeDao.insert(employee);
    }

    @Override
    @Transactional
    public void insertWithTransactional(Employee employee) {
        employeeDao.insert(employee);
    }

    @Override
    public void insertListWithoutTransactional(List<Employee> employees) {
        for (Employee employee : employees) {
            employeeDao.insert(employee);
        }
    }

    @Override
    @Transactional
    public void insertListWithTransactional(List<Employee> employees) {
        for (Employee employee : employees) {
            insertWithTransactional(employee);
        }
    }

    @Override
    public void insertListWithoutTransactional_ButCallInsertListWithTransactional(List<Employee> employees) {
        insertListWithTransactional(employees);
    }

    @Override
    public List<Employee> getAll() {
        return employeeDao.getAll();
    }

}