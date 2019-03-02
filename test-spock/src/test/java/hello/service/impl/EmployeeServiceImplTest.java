package hello.service.impl;

import hello.entity.Employee;
import hello.exception.ResourceNotFoundException;
import hello.repository.EmployeeRepository;
import hello.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {

    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @Before
    public void setUp() throws Exception {
        employeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getById_WhenNotExist_ShouldThrowException() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        Employee byId = employeeService.getById(123L);
    }
}