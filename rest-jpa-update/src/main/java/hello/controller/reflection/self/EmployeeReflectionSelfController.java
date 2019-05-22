package hello.controller.reflection.self;

import hello.entity.Employee;
import hello.service.reflection.self.EmployeeReflectionSelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(EmployeeReflectionSelfController.PATH)
public class EmployeeReflectionSelfController {

    public final static String PATH = "/reflection-self-employees";

    @Autowired
    private EmployeeReflectionSelfService service;

    @PatchMapping("/{id}")
    public Employee update(@RequestBody Map<String, Object> requestMap, @PathVariable Long id) {
        return service.update(id, requestMap);
    }
}
