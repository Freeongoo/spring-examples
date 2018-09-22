package examples.service;

import examples.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class GitHubLookupCompletableFutureService {

    private static final Logger logger = LoggerFactory.getLogger(GitHubLookupCompletableFutureService.class);

    private final RestTemplate restTemplate;

    public GitHubLookupCompletableFutureService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    // Spring’s @Async annotation, indicating it will run on a separate thread
    // it must be applied to public methods only
    // self-invocation – calling the async method from within the same class – won’t work
    @Async
    public CompletableFuture<User> findUser(String user) throws InterruptedException {
        logger.info("Looking up " + user);
        String url = String.format("https://api.github.com/users/%s", user);
        User results = restTemplate.getForObject(url, User.class);
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(results);
    }

}
