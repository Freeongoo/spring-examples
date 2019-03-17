package hello.beanValidation.customAnnotation;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class PersonWithCustomAnnotationTest {

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void validate_WhenAgeMoreZero_ShouldNotExistErrors() {
        PersonWithCustomAnnotation person = new PersonWithCustomAnnotation(20);

        Set<ConstraintViolation<PersonWithCustomAnnotation>> validates = validator.validate(person);

        // if not empty - exist error
        assertEquals(0, validates.size());
    }

    @Test
    public void validate_WhenAgeLessZero_ShouldBeErrors() {
        PersonWithCustomAnnotation person = new PersonWithCustomAnnotation(-20);

        Set<ConstraintViolation<PersonWithCustomAnnotation>> validates = validator.validate(person);

        // if not empty - exist error
        assertEquals(1, validates.size());
        assertThat(validates, containsInAnyOrder(
                hasProperty("message", is("Negative value"))
        ));
    }
}