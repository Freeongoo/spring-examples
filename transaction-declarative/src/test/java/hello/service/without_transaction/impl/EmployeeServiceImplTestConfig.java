package hello.service.without_transaction.impl;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;

@Configuration
@ComponentScan("hello")
@EnableMBeanExport(registration=RegistrationPolicy.IGNORE_EXISTING)
public class EmployeeServiceImplTestConfig {

}