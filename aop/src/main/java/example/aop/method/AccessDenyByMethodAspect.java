package example.aop.method;

import example.exceptions.NotAllowAccessCheckedException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AccessDenyByMethodAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // only for method
    @Before("execution(* example.aop.method.User.getPassword())")
    public void before(JoinPoint joinPoint) throws NotAllowAccessCheckedException {
        logger.info("before");
        throw new NotAllowAccessCheckedException("denied");
    }
}
