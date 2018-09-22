package examples.event.application;

import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStarting implements ApplicationListener<ApplicationStartingEvent> {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ApplicationStarting.class);

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        // Event published as early as conceivably possible - not display
        logger.info("------ ApplicationStarting run ------");
    }
}
