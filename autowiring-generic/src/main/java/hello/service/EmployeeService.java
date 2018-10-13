package hello.service;

import hello.model.Employee;
import hello.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    // it's not work - because we add specific realisation in specific implementation - findByName!
    // @Autowired
    // private JpaRepository<Employee, Long> employeeRepository;

    public List<Employee> getByName(String name) {
        return employeeRepository.findByName(name);
    }
}
