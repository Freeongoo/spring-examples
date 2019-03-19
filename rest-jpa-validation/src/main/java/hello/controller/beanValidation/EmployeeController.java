package hello.controller.beanValidation;

import hello.entity.beanValidation.Employee;
import hello.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
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
    public Employee create(@RequestBody @Valid Employee newEmployee) {
        return service.save(newEmployee);
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Employee update(@RequestBody Employee newEmployee, @PathVariable Long id) {
        return service.update(id, newEmployee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
