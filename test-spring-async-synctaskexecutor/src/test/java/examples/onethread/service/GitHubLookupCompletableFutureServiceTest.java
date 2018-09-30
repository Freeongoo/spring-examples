package examples.onethread.service;

import examples.ClassTestConfig;
import examples.model.User;
import examples.multithreads.service.GitHubLookupCompletableFutureService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        ClassTestConfig.class
})
@ActiveProfiles(profiles = "non-async")
public class GitHubLookupCompletableFutureServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(GitHubLookupCompletableFutureServiceTest.class);

    @Autowired
    private GitHubLookupCompletableFutureService service;

    @Test
    public void findUser() throws InterruptedException, ExecutionException {
        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        CompletableFuture<User> page1 = service.findUser("PivotalSoftware");
        CompletableFuture<User> page2 = service.findUser("CloudFoundry");
        CompletableFuture<User> page3 = service.findUser("Spring-Projects");

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