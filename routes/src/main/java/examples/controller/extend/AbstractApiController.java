package examples.controller.extend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class AbstractApiController {

    @GetMapping("")
    public String get() {
        return "getMethod";
    }
}
