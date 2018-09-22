package examples.service;

import examples.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class UserCreatorFutureService {
    private static final Logger logger = LoggerFactory.getLogger(UserCreatorFutureService.class);

    @Async
    public Future<User> createAndReturnUser() {
        logger.info("Try create user... ");
        try {
            User user = new User();
            user.setBlog("my Blog");
            user.setName("Ahaha");
            Thread.sleep(5000);
            return new AsyncResult<>(user);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
