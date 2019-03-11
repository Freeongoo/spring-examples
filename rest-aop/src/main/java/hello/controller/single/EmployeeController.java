package hello.controller.single;

import hello.entity.single.Employee;
import hello.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Example REST controller
 */
@RestController
@RequestMapping(EmployeeController.PATH)
public class EmployeeController {

    public final static String PATH = "/employees";

    private final EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    /** create for test {@link hello.apect.SecurityAccessAspect} */
    @RequestMapping(value = "/personal", method = RequestMethod.GET)
    public Iterable<Employee> getPersonalInfo() {
        return service.getAll();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Employee> all() {
        return service.getAll();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Employee newEmployee(@RequestBody Employee newEmployee) {
        return service.save(newEmployee);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Employee one(@PathVariable Long id) {
        return service.getById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        return service.update(id, newEmployee);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteEmployee(@PathVariable Long id) {
        service.delete(id);
    }
}
