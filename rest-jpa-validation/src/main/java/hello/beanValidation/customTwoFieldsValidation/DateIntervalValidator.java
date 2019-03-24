package hello.beanValidation.customTwoFieldsValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateIntervalValidator implements ConstraintValidator<ValidDateInterval, DateIntervalBean> {

    @Override
    public boolean isValid(DateIntervalBean bean, ConstraintValidatorContext context) {
        if (bean.getStartTime() == null || bean.getFinishTime() == null) {
            return true;
        }

        return !bean.getStartTime().after(bean.getFinishTime());
    }
}
