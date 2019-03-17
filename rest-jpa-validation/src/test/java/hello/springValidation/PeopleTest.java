package hello.springValidation;

import hello.springValidation.service.PeopleValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PeopleTest {

    @Autowired
    private PeopleValidator peopleValidator;

    @Test
    public void testValidators() {
        final People people = new People(-20);

        final DataBinder dataBinder = new DataBinder(people);
        dataBinder.addValidators(peopleValidator);
        dataBinder.validate();

        Assert.assertTrue(dataBinder.getBindingResult().hasErrors());

        List<ObjectError> allErrors = dataBinder.getBindingResult().getAllErrors();
        assertThat(allErrors, containsInAnyOrder(
                hasProperty("code", is("Negative value"))
        ));
    }
}