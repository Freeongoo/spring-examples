package examples.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

@Service
@Configuration
public class SimpleVoidService {
    private static final Logger logger = LoggerFactory.getLogger(SimpleVoidService.class);

    // set custom executor
    @Async("threadPoolTaskExecutor")
    public void asyncMethodWithVoidReturnType() throws InterruptedException {
        logger.info("Execute method asynchronously. " + Thread.currentThread().getName());
        Thread.sleep(4000L);
        logger.info("Execute method asynchronously done. " + Thread.currentThread().getName());
    }

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor myOnMethod = new ThreadPoolTaskExecutor();
        myOnMethod.setThreadNamePrefix("ExeForMethod-");
        return myOnMethod;
    }
}
