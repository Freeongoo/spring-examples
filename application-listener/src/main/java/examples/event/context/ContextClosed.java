package examples.event.context;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextClosed implements ApplicationListener<ContextClosedEvent> {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ContextClosed.class);

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        logger.info("------ ContextClosed run ------");
    }
}
