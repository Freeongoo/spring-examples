package hello.service.rawJson;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.entity.Employee;
import hello.exception.ResourceNotFoundException;
import hello.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static hello.exception.ErrorCode.OBJECT_NOT_FOUND;

@Service
public class EmployeeRawJsonService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee update(Long id, String json) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee
                .map(e -> getUpdatedFromJson(e, json))
                .orElseThrow(() -> new ResourceNotFoundException("Not exist by id", OBJECT_NOT_FOUND));
    }

    private Employee getUpdatedFromJson(Employee employee, String json) {
        Long id = employee.getId();

        updateFromJson(employee, json);

        employee.setId(id);
        return employeeRepository.save(employee);
    }

    private void updateFromJson(Employee employee, String json) {
        try {
            new ObjectMapper().readerForUpdating(employee).readValue(json);
        } catch (IOException e) {
            throw new RuntimeException("Cannot update from json", e);
        }
    }
}
