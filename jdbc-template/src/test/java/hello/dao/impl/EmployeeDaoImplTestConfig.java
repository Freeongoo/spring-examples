package hello.dao.impl;

import hello.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan("hello")
public class EmployeeDaoImplTestConfig {

    @Autowired
    private DataSource dataSource;

    // not need, but only for example, because this implementation will be found in package "hello"
    @Bean
    public EmployeeDao employeeDao() {
        return new EmployeeDaoImpl(dataSource);
    }
}
