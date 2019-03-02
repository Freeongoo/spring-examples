package hello.service.impl;

import hello.entity.Employee;
import hello.repository.EmployeeRepository;
import hello.service.AbstractService;
import hello.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends AbstractService<Employee, Long> implements EmployeeService {

    private final EmployeeRepository repository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    protected CrudRepository<Employee, Long> getRepository() {
        return repository;
    }
}
