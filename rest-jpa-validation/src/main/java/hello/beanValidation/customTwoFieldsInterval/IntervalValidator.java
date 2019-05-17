package hello.beanValidation.customTwoFieldsInterval;

import hello.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class IntervalValidator implements ConstraintValidator<ValidInterval, Object> {

    private String startField;
    private String endField;
    private boolean isMayBeEqual;

    @Override
    public void initialize(ValidInterval constraintAnnotation) {
        startField = constraintAnnotation.startField();
        endField = constraintAnnotation.endField();
        isMayBeEqual = constraintAnnotation.isMayBeEqual();
    }

    @Override
    public boolean isValid(Object bean, ConstraintValidatorContext context) {
        Object start = ReflectionUtils.getFieldContent(bean, startField);
        Object end = ReflectionUtils.getFieldContent(bean, endField);

        if (start == null || end == null) return true;

        if (start instanceof Number && end instanceof Number) {
            double doubleStart = ((Number) start).doubleValue();
            double doubleFinish = ((Number) end).doubleValue();

            if (isMayBeEqual) {
                return doubleStart <= doubleFinish;
            }
            return doubleStart < doubleFinish;
        }

        if (start instanceof Date && end instanceof Date) {
            long timeFrom = ((Date) start).getTime();
            long timeTo = ((Date) end).getTime();

            if (isMayBeEqual) {
                return timeFrom <= timeTo;
            }
            return timeFrom <= timeTo;
        }

        throw new RuntimeException("Can compare only number fields or date");
    }
}
