package hello.beanValidation.customTwoFieldsInterval;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(TYPE)  // for class
@Retention(RUNTIME)
public @interface ValidIntervals {
    ValidInterval[] value();
}
