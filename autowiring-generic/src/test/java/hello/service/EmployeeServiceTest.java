package hello.service;

import hello.model.Employee;
import hello.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void getByName() {
        Employee employee = new Employee();
        employee.setName("name");

        ArrayList<Employee> expectedList = new ArrayList<>();
        expectedList.add(employee);

        when(employeeRepository.findByName(any())).thenReturn(expectedList);

        String name = "name";
        List<Employee> employeeList = employeeService.getByName(name);

        assertThat(employeeList, equalTo(expectedList));
    }
}