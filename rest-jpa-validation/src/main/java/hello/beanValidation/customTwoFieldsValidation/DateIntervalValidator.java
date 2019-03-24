package hello.beanValidation.customTwoFieldsValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateIntervalValidator implements ConstraintValidator<ValidDateInterval, Operation> {

    @Override
    public boolean isValid(Operation operation, ConstraintValidatorContext context) {
        if (operation.getStartTime() == null || operation.getFinishTime() == null) {
            return true;
        }

        return !operation.getStartTime().after(operation.getFinishTime());
    }
}
