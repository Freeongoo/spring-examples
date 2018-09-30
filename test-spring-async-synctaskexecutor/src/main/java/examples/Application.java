package examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /*private void createAndReturnUserTest() throws ExecutionException, InterruptedException {
        // Start the clock
        long start = System.currentTimeMillis();
        Future<User> futureUser = userCreatorFutureService.createAndReturnUser();

        // wait until get user
        User user = futureUser.get();

        // Print results, including elapsed time
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("Created user --> " + user);
    }*/
}