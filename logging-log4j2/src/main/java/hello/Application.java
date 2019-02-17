package hello;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    private static final Logger logger = LogManager.getLogger(Application.class);

	public static void main(String[] args) {
        someLogMsg();

        SpringApplication.run(Application.class, args);
    }

    private static void someLogMsg() {
        logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");
    }
}            