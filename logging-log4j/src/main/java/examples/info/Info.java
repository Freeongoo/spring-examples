package examples.info;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Info {

    private static final Logger logger = Logger.getLogger(Info.class);

    public void log() {
        logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");
    }
}
