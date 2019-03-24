package hello.beanValidation.customTwoFieldsValidation;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class OperationTest {

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void validate_WhenStartTimeLessFinishTime_ShouldNotExistErrors() throws ParseException {
        Date startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2017-02-02T10:10:10");
        Date finishTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2017-02-02T12:10:10");
        Operation operation = new Operation(startTime, finishTime);

        Set<ConstraintViolation<Operation>> constraintViolations = validator.validate(operation);

        // if not empty - exist error
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validate_WhenStartTimeEqualsFinishTime_ShouldNotExistErrors() throws ParseException {
        Date startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2017-02-02T10:10:10");
        Date finishTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2017-02-02T10:10:10");
        Operation operation = new Operation(startTime, finishTime);

        Set<ConstraintViolation<Operation>> constraintViolations = validator.validate(operation);

        // if not empty - exist error
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void validate_WhenStartTimeMoreFinishTime_ShouldBeErrors() throws ParseException {
        Date startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2017-02-02T10:10:10");
        Date finishTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2017-02-02T09:10:10");
        Operation operation = new Operation(startTime, finishTime);

        Set<ConstraintViolation<Operation>> constraintViolations = validator.validate(operation);

        // if not empty - exist error
        assertEquals(1, constraintViolations.size());
        assertThat(constraintViolations, containsInAnyOrder(
                hasProperty("message", is("Start time cannot be more than finish time"))
        ));
    }
}