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
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceOnlyCommonRealizationTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceOnlyCommonRealization employeeService;

    @Test
    public void getByName() {
        Employee employee = new Employee();
        employee.setId(2L);

        when(employeeRepository.getOne(2L)).thenReturn(employee);

        Employee actualEmployee = employeeService.get(2L);

        assertThat(actualEmployee, equalTo(employee));
    }
}