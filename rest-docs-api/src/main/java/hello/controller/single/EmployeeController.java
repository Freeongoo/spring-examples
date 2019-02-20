package hello.controller.single;

import hello.entity.single.Employee;
import hello.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Example REST controller with set location in header when update or create
 */
@RestController
@RequestMapping(EmployeeController.PATH)
public class EmployeeController {

    public final static String PATH = "/employees";

    @Autowired
    private EmployeeService service;

    @GetMapping("")
    public Iterable<Employee> all() {
        return service.getAll();
    }

    @PostMapping("")
    public ResponseEntity<Employee> newEmployee(@RequestBody Employee newEmployee, UriComponentsBuilder ucBuilder) {
        Employee employeeSaved = service.save(newEmployee);

        return getHeaderWithLocation(ucBuilder, employeeSaved, PATH + "/{id}");
    }

    @GetMapping("/{id}")
    public Employee one(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id, UriComponentsBuilder ucBuilder) {
        Employee updatedEmployee = service.update(id, newEmployee);

        return getHeaderWithLocation(ucBuilder, updatedEmployee, "/employees/{id}");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Employee> getHeaderWithLocation(UriComponentsBuilder ucBuilder, Employee employeeSaved, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path(url).buildAndExpand(employeeSaved.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
