package hello.service.impl;

import java.util.List;

import hello.dao.EmployeeDao;
import hello.model.Employee;
import hello.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeDao employeeDao;

    @Override
    public void insert(Employee employee) {
        employeeDao.insert(employee);
    }

    @Override
    public int insertWithReturnInsertedId(Employee employee) {
        return employeeDao.insertWithReturnInsertedId(employee);
    }

    @Override
    public void insertList(List<Employee> employees) {
        employeeDao.insertList(employees);
    }

    @Override
    public List<Employee> getAll() {
        return employeeDao.getAll();
    }

    @Override
    public Employee getById(int employeeId) {
        try {
            return employeeDao.getById(employeeId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Employee getByEmail(String email) {
        try {
            return employeeDao.getByEmail(email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void delete(Employee employee) {
        employeeDao.delete(employee);
    }

}