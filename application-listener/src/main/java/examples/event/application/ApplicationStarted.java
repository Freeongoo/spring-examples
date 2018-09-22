package examples.event.application;

import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStarted implements ApplicationListener<ApplicationStartedEvent> {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ApplicationStarted.class);

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        logger.info("------ ApplicationStarted run ------");
    }
}
