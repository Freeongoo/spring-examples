package examples.controller;

import examples.model.annotations.DataWithAnnotationValidate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AnnotationValidateDataController {

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("data", new DataWithAnnotationValidate());
        return "form";
    }

    @PostMapping("/")
    public String greetingSubmit(@Valid DataWithAnnotationValidate data, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "form";

        model.addAttribute("data", data);
        return "result";
    }
}
