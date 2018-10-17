package example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableAspectJAutoProxy(exposeProxy=true)
public class Beans {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder(4);
	}
	
   @Bean
   public TaskScheduler taskScheduler() {
       return new ConcurrentTaskScheduler();
   }
   
   @Bean
   public TaskExecutor taskExecutor() {
       return new SimpleAsyncTaskExecutor(); // Or use another one of your liking
   }
}
