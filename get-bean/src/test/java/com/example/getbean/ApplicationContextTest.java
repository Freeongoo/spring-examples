package com.example.getbean;

import com.example.getbean.service.ServiceA;
import com.example.getbean.service.a.SomeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationContextTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void getBean_ByClassName() {
        ServiceA serviceA = applicationContext.getBean(ServiceA.class);
        assertNotNull(serviceA);
    }

    @Test
    public void getBean_ByBeanName() {
        Object serviceA = applicationContext.getBean("serviceA");
        assertTrue(serviceA instanceof ServiceA);
    }

    @Test
    public void getBean_ByBeanNameAndClass() {
        ServiceA serviceA = applicationContext.getBean("serviceA", ServiceA.class);
        assertNotNull(serviceA);
    }

    @Test
    public void getBean_ByBeanNameAndClass_OtherPackage() {
        SomeService servicePackageA = applicationContext.getBean("servicePackageA", SomeService.class);
        assertNotNull(servicePackageA);
    }
}