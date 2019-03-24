package hello.beanValidation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.*;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class PersonTest {

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void validate_WhenAllOk_ShouldNotExistErrors() {
        Person person = new Person("John", 20);

        Set<ConstraintViolation<Person>> validates = validator.validate(person);

        // if not empty - exist error
        assertEquals(0, validates.size());
    }

    @Test
    public void validate_WhenInvalidName_DefaultValidationMessage() {
        Person person = new Person("J", 20);

        Set<ConstraintViolation<Person>> validates = validator.validate(person);
        Assert.assertTrue(validates.size() > 0);

        assertThat(validates, containsInAnyOrder(
                hasProperty("message", is("size must be between 2 and 50"))
        ));
    }

    @Test
    public void validate_WhenInvalidSecondName_DefaultValidationMessage() {
        Person person = new Person("John", "M", 20);

        Set<ConstraintViolation<Person>> validates = validator.validate(person);
        Assert.assertTrue(validates.size() > 0);

        for(ConstraintViolation<Person> constraintViolation : validates) {
            Path propertyPath = constraintViolation.getPropertyPath();
            System.out.println(propertyPath);
        }

        assertThat(validates, containsInAnyOrder(
                hasProperty("message", is("Param value: 'M' size must be between 2 and 50"))
        ));
    }

    @Test
    public void validate_WhenInvalidAge_CustomValidationMessage() {
        Person person = new Person("John", -4500);

        Set<ConstraintViolation<Person>> validates = validator.validate(person);

        // if not empty - exist error
        Assert.assertTrue(validates.size() > 0);

        assertThat(validates, containsInAnyOrder(
                hasProperty("message", is("Not more 3 chars"))
        ));
    }
}