package hello.controller.rawJson;

import hello.entity.Employee;
import hello.service.rawJson.EmployeeRawJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EmployeeRawJsonController.PATH)
public class EmployeeRawJsonController {

    public final static String PATH = "/raw-json-employees";

    @Autowired
    private EmployeeRawJsonService service;

    @PatchMapping("/{id}")
    public Employee update(@RequestBody String json, @PathVariable Long id) {
        return service.update(id, json);
    }
}
