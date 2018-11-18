package examples.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class LocalController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/local")
    public String local(Locale locale) {
        return locale.toString();
    }

    @RequestMapping("/trulala")
    public String trulala(Locale locale) {
        return messageSource.getMessage("trulala", null, locale);
    }
}
