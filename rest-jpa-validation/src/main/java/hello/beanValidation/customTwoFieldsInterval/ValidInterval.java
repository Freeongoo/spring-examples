package hello.beanValidation.customTwoFieldsInterval;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(TYPE)  // for class
@Retention(RUNTIME)
@Constraint(validatedBy = { IntervalValidator.class })
@Repeatable(ValidIntervals.class)   // for repeated annotation
public @interface ValidInterval {

    String startField();

    String endField();

    String message() default "startField: '{startField}' cannot be more than endField: '{endField}'";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
