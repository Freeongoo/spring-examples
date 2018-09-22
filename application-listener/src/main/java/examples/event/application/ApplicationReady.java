package examples.event.application;

import examples.util.DateUtil;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationReady implements ApplicationListener<ApplicationReadyEvent> {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ApplicationReady.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        // can get a bean from context here...
        // applicationContext.getBean("beanName");

        logger.info("------ ApplicationReady run at time: ------" + DateUtil.convertTime(event.getTimestamp()));
    }
}
