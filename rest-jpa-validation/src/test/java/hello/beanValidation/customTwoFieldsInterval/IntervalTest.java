package hello.beanValidation.customTwoFieldsInterval;

import org.junit.*;
import org.junit.rules.ExpectedException;

import javax.validation.*;
import java.util.Set;

import static org.junit.Assert.assertEquals;

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
    public void validate_WhenDouble() {
        Interval interval = new Interval(3., 1.);

        Set<ConstraintViolation<Interval>> validate = validator.validate(interval);

        // if not empty - exist error
        assertEquals(1, validate.size());
    }

    @Test
    public void validate_WhenInteger() {
        Interval interval = new Interval(2, 1);

        Set<ConstraintViolation<Interval>> validate = validator.validate(interval);

        // if not empty - exist error
        assertEquals(1, validate.size());
    }

    @Test
    public void validate_WhenIntegerAndDouble() {
        Interval interval = new Interval(3., 1., 2, 1);

        Set<ConstraintViolation<Interval>> validate = validator.validate(interval);

        // if not empty - exist error
        assertEquals(2, validate.size());
    }
}