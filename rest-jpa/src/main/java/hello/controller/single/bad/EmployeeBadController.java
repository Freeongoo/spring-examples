package hello.controller.single.bad;

import hello.entity.single.Employee;
import hello.exception.EmployeeNotFoundException;
import hello.exception.ErrorCode;
import hello.repository.single.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Bad practices - need move logic from controller to service.
 * See better example {@link hello.controller.single.EmployeeController}
 */
@RestController
@RequestMapping(EmployeeBadController.PATH)
public class EmployeeBadController {

    public final static String PATH = "/bad-employees";

    @Autowired
    private EmployeeRepository repository;

    @GetMapping("")
    public List<Employee> all() {
        return repository.findAll();
    }

    @PostMapping("")
    public ResponseEntity<Employee> newEmployee(@RequestBody Employee newEmployee, UriComponentsBuilder ucBuilder) {
        Employee employeeSaved = repository.save(newEmployee);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path(PATH + "/{id}").buildAndExpand(employeeSaved.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Employee one(@PathVariable Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not find by id: " + id, ErrorCode.OBJECT_NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id, UriComponentsBuilder ucBuilder) {
        Employee updatedEmployee = repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/employees/{id}").buildAndExpand(updatedEmployee.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
