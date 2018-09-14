package examples.controller;

import examples.model.Employee;
import examples.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestControllerEmployee {
    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value="/employees/{id}", method=RequestMethod.GET)
    public Employee employee(@PathVariable int id) {
        return employeeService.getById(id);
    }

    @RequestMapping(value="/employees", method=RequestMethod.GET)
    public List<Employee> employeeList() {
        return employeeService.getAll();
    }
}
