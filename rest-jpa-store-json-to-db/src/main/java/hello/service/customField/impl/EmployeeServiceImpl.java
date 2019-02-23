package hello.service.customField.impl;

import hello.entity.customField.Employee;
import hello.repository.customField.EmployeeRepository;
import hello.service.AbstractService;
import hello.service.customField.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends AbstractService<Employee, Long> implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    protected CrudRepository<Employee, Long> getRepository() {
        return repository;
    }
}
