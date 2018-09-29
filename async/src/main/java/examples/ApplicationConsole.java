package examples;

import examples.model.User;
import examples.multithreads.service.GitHubLookupCompletableFutureService;
import examples.multithreads.service.SimpleVoidService;
import examples.multithreads.service.UserCreatorFutureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootApplication
@EnableAsync
public class ApplicationConsole implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationConsole.class);

    private final GitHubLookupCompletableFutureService gitHubLookupCompletableFutureService;
    private final SimpleVoidService simpleVoidService;
    private final UserCreatorFutureService userCreatorFutureService;

    public ApplicationConsole(
            GitHubLookupCompletableFutureService gitHubLookupCompletableFutureService,
            SimpleVoidService simpleVoidService,
            UserCreatorFutureService userCreatorFutureService
    ) {
        this.simpleVoidService = simpleVoidService;
        this.gitHubLookupCompletableFutureService = gitHubLookupCompletableFutureService;
        this.userCreatorFutureService = userCreatorFutureService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConsole.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // run void async with local config executor
        runVoidAsync();
        // run async with CompletableFuture
        checkGitHubWithCompletableFuture();
        // run async with Future
        createAndReturnUserTest();
    }

    // run and forget - nothing return :)
    private void runVoidAsync() throws InterruptedException {
        simpleVoidService.asyncMethodWithVoidReturnType();
    }

    private void checkGitHubWithCompletableFuture() throws InterruptedException, java.util.concurrent.ExecutionException {
        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        CompletableFuture<User> page1 = gitHubLookupCompletableFutureService.findUser("PivotalSoftware");
        CompletableFuture<User> page2 = gitHubLookupCompletableFutureService.findUser("CloudFoundry");
        CompletableFuture<User> page3 = gitHubLookupCompletableFutureService.findUser("Spring-Projects");

        // do something other...

        // Wait until they are all done
        CompletableFuture.allOf(page1,page2,page3).join();

        // Print results, including elapsed time
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("--> " + page1.get());
        logger.info("--> " + page2.get());
        logger.info("--> " + page3.get());
    }

    private void createAndReturnUserTest() throws ExecutionException, InterruptedException {
        // Start the clock
        long start = System.currentTimeMillis();
        Future<User> futureUser = userCreatorFutureService.createAndReturnUser();

        // wait until get user
        User user = futureUser.get();

        // Print results, including elapsed time
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("Created user --> " + user);
    }
}