package hello.mocking.service;

import hello.dao.EmployeeDao;
import hello.model.Employee;
import hello.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceImplTest {
    @MockBean
    private EmployeeDao employeeDao;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void getById() {
        Employee employee = new Employee();
        employee.setId(2);
        when(employeeDao.getById(2)).thenReturn(employee);

        Employee actualEmployee = employeeService.getById(2);

        assertThat(actualEmployee, equalTo(employee));
    }

    @Test
    public void getById_WhenNotExistById() {
        Employee employee = new Employee();
        employee.setId(2);
        when(employeeDao.getById(2)).thenReturn(employee);
        int NotExistId = -2;

        Employee actualEmployee = employeeService.getById(NotExistId);

        assertNull(actualEmployee);
    }
}