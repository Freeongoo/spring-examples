package hello.service.impl;

import hello.entity.single.Employee;
import hello.repository.single.EmployeeRepository;
import hello.service.EmployeeService;
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
