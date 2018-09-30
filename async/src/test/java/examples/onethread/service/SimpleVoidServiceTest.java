package examples.onethread.service;

import examples.ClassTestConfig;
import examples.TestApplication;
import examples.multithreads.service.SimpleVoidService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        TestApplication.class,
        ClassTestConfig.class
})
public class SimpleVoidServiceTest {
    @Autowired
    private SimpleVoidService service;

    @Test
    public void asyncMethodWithVoidReturnType() throws InterruptedException {
        service.asyncMethodWithVoidReturnType();
        Thread.sleep(5000);
    }
}