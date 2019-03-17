package hello.springValidation.service;

import hello.springValidation.People;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class PeopleValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return People.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        People p = (People) target;
        if (p.getAge() < 0) {
            errors.rejectValue("age", "Negative value");
        }
    }
}
