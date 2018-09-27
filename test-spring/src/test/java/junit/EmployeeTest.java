package junit;


import hello.model.Employee;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNot.not;

public class EmployeeTest {
    private Employee employee;

    @Before
    public void setUp() {
        employee = createEmployee();
    }

    @Test
    public void getId() {
        int expectedId = 1;
        assertThat(employee.getId(), is(expectedId));
    }

    @Test
    public void setId() {
        int expectedId = 10;
        employee.setId(expectedId);

        assertThat(employee.getId(), is(expectedId));
    }

    @Test
    public void getName() {
        String nameExpected = "John";
        assertThat(employee.getName(), is(nameExpected));
    }

    @Test
    public void setName() {
        String nameExpected = "JohnNew";
        employee.setName(nameExpected);

        assertThat(employee.getName(), is(nameExpected));
    }

    @Test
    public void getEmail() {
        String emailExpected = "john@example.com";
        assertThat(employee.getEmail(), is(emailExpected));
    }

    @Test
    public void setEmail() {
        String emailExpected = "john_new@example.com";
        employee.setEmail(emailExpected);

        assertThat(employee.getEmail(), is(emailExpected));
    }

    @Test
    public void equals() {
        Employee employee1 = new Employee(1, "John", "john@example.com");

        assertThat(employee, is(employee1));
    }

    @Test
    public void equals_WhenNot() {
        Employee employee1 = new Employee(2, "John", "john@example.com");

        assertThat(employee, is(not(employee1)));
    }

    private Employee createEmployee() {
        return new Employee(1, "John", "john@example.com");
    }
}