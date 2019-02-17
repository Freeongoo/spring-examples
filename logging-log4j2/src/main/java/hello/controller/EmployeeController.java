package hello.controller;

import hello.entity.Employee;
import hello.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = EmployeeController.PATH, produces = APPLICATION_JSON_VALUE)
public class EmployeeController {

    public final static String PATH = "/employees";

    private final EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("")
    Iterable<Employee> getAll() {
        return service.getAll();
    }

    @PostMapping("")
    Employee create(@RequestBody Employee post) {
        return service.save(post);
    }

    @GetMapping("/{id}")
    Employee getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    Employee update(@RequestBody Employee post, @PathVariable Long id) {
        return service.update(id, post);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
