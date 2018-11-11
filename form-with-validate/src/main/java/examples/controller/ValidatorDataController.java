package examples.controller;

import examples.model.Data;
import examples.model.annotations.DataWithAnnotationValidate;
import examples.validate.DataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ValidatorDataController {

    @Autowired
    private DataValidator dataValidator;

    @GetMapping("/validator")
    public String main(Model model) {
        model.addAttribute("data", new Data());
        return "form.validator";
    }

    @PostMapping("/validator")
    public String greetingSubmit(Data data, BindingResult bindingResult, Model model) {
        dataValidator.validate(data, bindingResult);

        if (bindingResult.hasErrors())
            return "form.validator";

        model.addAttribute("data", data);
        return "result.validator";
    }
}
