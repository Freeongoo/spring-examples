package examples.controller;

import examples.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class HomeController {

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("data", new Data());
        return "form";
    }

    @PostMapping("/")
    public String greetingSubmit(@Valid Data data, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "form";
        }

        model.addAttribute("data", data);
        return "result";
    }
}
