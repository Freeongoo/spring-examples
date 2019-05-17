package hello.beanValidation.customTwoFieldsInterval;

import hello.beanValidation.customTwoFieldsDate.Operation;
import org.junit.*;
import org.junit.rules.ExpectedException;

import javax.validation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author dorofeev
 */
public class IntervalTest {

    private Validator validator;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void validate() {
        Interval interval = new Interval(3., 1.);

        Set<ConstraintViolation<Interval>> validate = validator.validate(interval);

        // if not empty - exist error
        assertEquals(1, validate.size());
    }

}