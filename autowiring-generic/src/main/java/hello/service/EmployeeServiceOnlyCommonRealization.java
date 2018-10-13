package hello.service;

import hello.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceOnlyCommonRealization {
    @Autowired
    private JpaRepository<Employee, Long> employeeRepository;

    public Employee get(Long id) {
        return employeeRepository.getOne(id);
    }
}

