package examples.configasync;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class LocalAsyncConfig {

    @Bean(name = "localThreadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor myOnMethod = new ThreadPoolTaskExecutor();
        myOnMethod.setCorePoolSize(1);
        myOnMethod.setMaxPoolSize(1);
        myOnMethod.setQueueCapacity(1);
        myOnMethod.setThreadNamePrefix("LocExecutor-");
        return myOnMethod;
    }
}
