package hello;

import hello.model.Employee;
import hello.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {
    @Autowired
    EmployeeService employeeService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        EmployeeService employeeService = context.getBean(EmployeeService.class);

        // create new and get from inserted id
        Employee employee = new Employee("Free", "test@test.com");
        int insertedId = employeeService.insertWithReturnInsertedId(employee);
        Employee employeeFromId = employeeService.getById(insertedId);
        System.out.println(employeeFromId);

        // try insert when same email - email is unique - throw DuplicateKeyException
        // Employee employee2 = new Employee("Free2", "test@test.com");
        // employeeService.insert(employee2);

        // get from email
        Employee employeeFromEmail = employeeService.getByEmail("test@test.com");
        System.out.println(employeeFromEmail);

        // remove inserted employee
        employeeService.delete(employeeFromId);

        // get from not exist email
        Employee notExist = employeeService.getByEmail("notexist@test.com");
        System.out.println(notExist);

        List<Employee> listForInsert = new ArrayList<>();
        listForInsert.add(new Employee("Trulala", "ad@ad.com"));
        listForInsert.add(new Employee("Trulala2", "ad2@ad.com"));
        employeeService.insertList(listForInsert);

        // show all employees
        List<Employee> list = employeeService.getAll();
        list.forEach(System.out::println);

        ((ConfigurableApplicationContext) context).close();
    }
}