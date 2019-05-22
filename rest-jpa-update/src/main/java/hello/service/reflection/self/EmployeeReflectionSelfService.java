package hello.service.reflection.self;

import hello.entity.Employee;
import hello.exception.ResourceNotFoundException;
import hello.repository.EmployeeRepository;
import hello.util.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static hello.exception.ErrorCode.OBJECT_NOT_FOUND;

@Service
public class EmployeeReflectionSelfService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee update(Long id, Map<String, Object> requestMap) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee
                .map(e -> getUpdatedFromMap(e, requestMap))
                .orElseThrow(() -> new ResourceNotFoundException("Not exist by id", OBJECT_NOT_FOUND));
    }

    private Employee getUpdatedFromMap(Employee employeeFromDb, Map<String, Object> requestMap) {
        Long id = employeeFromDb.getId();

        requestMap.forEach((key, value) -> ReflectionUtils.setFieldContent(employeeFromDb, key, value));

        employeeFromDb.setId(id);
        return employeeRepository.save(employeeFromDb);
    }
}
