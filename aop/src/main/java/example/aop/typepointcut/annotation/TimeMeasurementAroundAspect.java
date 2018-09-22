package example.aop.typepointcut.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Aspect
@Configuration
public class TimeMeasurementAroundAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(example.customannotation.SpendTime)")
    public Object measurment (ProceedingJoinPoint pjp) throws Throwable{
        long startTime = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        logger.info("Runner " + pjp.getSignature() + ", args: " + Arrays.toString(pjp.getArgs()) + ". Finish " + (System.currentTimeMillis() - startTime) + " ms");
        return retVal;
    }
}
