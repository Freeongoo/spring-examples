package examples.validate;

import examples.model.Data;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class DataValidator implements Validator {
    private static final Pattern PATTERN_CONTEXT = Pattern.compile("^[a-zA-Z0-9]+$");

    @Override
    public boolean supports(Class<?> clazz) {
        return Data.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // validate empty
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "empty.id");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "empty.content");

        Data user = (Data) target;

        if (user.getId() <= 0L)
            errors.rejectValue("id", "not_zero.id");

        if (user.getContent().length() < 1 || user.getContent().length() > 20)
            errors.rejectValue("content", "invalid_length.content");

        if (!PATTERN_CONTEXT.matcher(user.getContent()).matches())
            errors.rejectValue("content", "invalid_data.content");
    }
}
