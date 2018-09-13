package hello;

import hello.model.Employee;
import hello.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {
    @Autowired
    EmployeeService employeeService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        EmployeeService employeeService = context.getBean(EmployeeService.class);

        Employee emp= new Employee();
        emp.setEmpId("emp");
        emp.setEmpName("emp");

        Employee emp1= new Employee();
        emp1.setEmpId("emp1");
        emp1.setEmpName("emp1");

        Employee emp2= new Employee();
        emp2.setEmpId("emp2");
        emp2.setEmpName("emp2");


        employeeService.insertEmployee(emp);

        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employeeService.insertEmployees(employees);

        employeeService.getAllEmployees();

        employeeService.getEmployeeById(emp1.getEmpId());
    }
}