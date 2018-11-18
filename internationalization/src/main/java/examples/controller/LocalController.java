package examples.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class LocalController {

    @RequestMapping("/local")
    public String home(Locale locale) {
        return locale.toString();
    }
}
