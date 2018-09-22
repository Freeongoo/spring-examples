package examples.event.context;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextStarted implements ApplicationListener<ContextStartedEvent> {
    private static final org.slf4j.Logger logger=LoggerFactory.getLogger(ContextStarted.class);

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        // ContextStartedEvent is published when you explicitly invoke ConfigurableApplicationContext.start() on the context. - not display
        logger.info("------ ContextStarted run ------");
    }
}
