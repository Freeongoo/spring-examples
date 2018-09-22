package example.aop.typepointcut.clazz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Flower {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void touch() {
        logger.info("touch");
    }
}
