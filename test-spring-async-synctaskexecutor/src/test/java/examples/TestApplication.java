package examples;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SyncTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
public class TestApplication extends Application {

    // for method where you want to use this type of Executor config set: @Async("localThreadPoolTaskExecutor")
    @Primary
    @Bean(name = "localThreadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        return new SyncTaskExecutor();
    }
}
