package hello.controller.springValidation;

import hello.exception.NotValidParamsException;
import hello.service.PeopleService;
import hello.springValidation.People;
import hello.springValidation.service.PeopleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static hello.exception.ErrorCode.INVALID_PARAMS;

@RestController
@RequestMapping(PeopleController.PATH)
public class PeopleController {

    public final static String PATH = "/peoples";

    @Autowired
    private PeopleService service;

    @Autowired
    private PeopleValidator peopleValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(peopleValidator);
    }

    @GetMapping("")
    public Iterable<People> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public People one(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("")
    public People create(@RequestBody @Valid People people, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new NotValidParamsException(bindingResult.getFieldError().getCode(), INVALID_PARAMS);
        }
        return service.save(people);
    }

    @PutMapping("/{id}")
    public People update(@RequestBody @Valid People people, BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            throw new NotValidParamsException(bindingResult.getFieldError().getCode(), INVALID_PARAMS);
        }
        return service.update(id, people);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
