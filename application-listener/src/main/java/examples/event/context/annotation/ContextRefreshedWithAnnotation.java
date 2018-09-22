package examples.event.context.annotation;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ContextRefreshedWithAnnotation {
    private static final org.slf4j.Logger logger=LoggerFactory.getLogger(ContextRefreshedWithAnnotation.class);

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        logger.info("------ ContextRefreshed with Annotation way run ------");
    }
}
