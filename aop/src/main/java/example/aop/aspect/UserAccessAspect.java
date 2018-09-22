package example.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

// Aspect: A combination of defining when you want to intercept a method call (Pointcut) and what to do (Advice) is called an Aspect.
@Aspect
@Configuration
public class UserAccessAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // This define "Pointcut" - the expression used to define when a call to a method should be intercepted. In the above
    @Before("execution(* example.aop.dao.*.*(..))")
    public void before(JoinPoint joinPoint) {
        // This is a "Advice"
        logger.info(" Check for user access ");
        logger.info(" Allowed execution for {}", joinPoint);
    }
}
