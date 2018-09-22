package examples.event.context;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextRefreshed implements ApplicationListener<ContextRefreshedEvent> {
    private static final org.slf4j.Logger logger=LoggerFactory.getLogger(ContextRefreshed.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("------ ContextRefreshed run ------");
    }
}
