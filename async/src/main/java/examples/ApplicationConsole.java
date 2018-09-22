package examples;

import examples.model.User;
import examples.service.GitHubLookupCompletableFutureService;
import examples.service.SimpleVoidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.CompletableFuture;

@SpringBootApplication
@EnableAsync
public class ApplicationConsole implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationConsole.class);

    private final GitHubLookupCompletableFutureService gitHubLookupCompletableFutureService;
    private final SimpleVoidService simpleVoidService;

    public ApplicationConsole(GitHubLookupCompletableFutureService gitHubLookupCompletableFutureService, SimpleVoidService simpleVoidService) {
        this.simpleVoidService = simpleVoidService;
        this.gitHubLookupCompletableFutureService = gitHubLookupCompletableFutureService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConsole.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        runVoidAsync();

        checkGitHubWithCompletableFuture();
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
}