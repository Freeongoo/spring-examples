package hello.controller.classic;

import hello.entity.Employee;
import hello.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Employee create(@RequestBody Employee employee) {
        return service.save(employee);
    }

    @GetMapping("/{id}")
    public Employee getByOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @PatchMapping("/{id}")
    public Employee update(@RequestBody Employee employee, @PathVariable Long id) {
        return service.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
