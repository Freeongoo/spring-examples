package example.aop.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class LoggingDeveloperAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* example.aop.common.forlogging.Developer.*(..))")
    public void selectAllMethodsAvailable() {

    }

    @Before("selectAllMethodsAvailable()")
    public void beforeAdvice(JoinPoint joinPoint) {
        logger.info("Inside beforeAdvice() method...");
        logger.info("@Before : " + joinPoint.getSignature().getName());
    }

    @After("selectAllMethodsAvailable()")
    public void afterAdvice(JoinPoint joinPoint) {
        logger.info("Inside beforeAdvice() method...");
        logger.info("@Before : " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "selectAllMethodsAvailable()", returning = "someValue")
    public void afterReturningAdvice(JoinPoint joinPoint, Object someValue) {
        logger.info("Inside afterReturningAdvice() method...");
        logger.info("returning after : " + joinPoint.getSignature().getName());
        logger.info("Method returned value is : " + someValue);
    }

    @AfterThrowing(pointcut = "selectAllMethodsAvailable()", throwing = "e")
    public void inCaseOfExceptionThrowAdvice(JoinPoint joinPoint, ClassCastException e) {
        logger.info("Inside inCaseOfExceptionThrowAdvice method...");
        logger.info("returning after : " + joinPoint.getSignature().getName());
        logger.info("We have an exception here: " + e.toString());
    }

}
