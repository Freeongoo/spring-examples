package hello.beanValidation.customTwoFieldsInterval;

import hello.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IntervalValidator implements ConstraintValidator<ValidInterval, Object> {

    private String startField;
    private String endField;

    @Override
    public void initialize(ValidInterval constraintAnnotation) {
        startField = constraintAnnotation.startField();
        endField = constraintAnnotation.endField();
    }

    @Override
    public boolean isValid(Object bean, ConstraintValidatorContext context) {
        Object start = ReflectionUtils.getFieldContent(bean, startField);
        Object end = ReflectionUtils.getFieldContent(bean, endField);

        if (start instanceof Number && end instanceof Number) {
            return ((Number) start).doubleValue() < ((Number) end).doubleValue();
        }

        throw new RuntimeException("Can compare only number fields");
    }
}
