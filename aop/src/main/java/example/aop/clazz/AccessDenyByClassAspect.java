package example.aop.clazz;

import example.exceptions.NotAllowAccessCheckedException;
import example.exceptions.NotAllowAccessRuntimeException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AccessDenyByClassAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // only for all methods class FlowerDontTouch
    @Before("execution(* example.aop.clazz.FlowerDontTouch.*())")
    public void before(JoinPoint joinPoint) {
        logger.info("before");
        throw new NotAllowAccessRuntimeException("denied");
    }
}
