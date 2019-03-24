package hello.beanValidation.customTwoFieldsValidation;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(TYPE)  // for class
@Retention(RUNTIME)
@Constraint(validatedBy = { DateIntervalValidator.class })
public @interface ValidDateInterval {

    String message() default "Start time cannot be more than finish time";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
