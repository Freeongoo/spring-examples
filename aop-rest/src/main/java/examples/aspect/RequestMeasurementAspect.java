package examples.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Aspect
@Configuration
public class RequestMeasurementAspect {
    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object measurement (ProceedingJoinPoint pjp) throws Throwable{
        long startTime = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        System.out.println("Request " + pjp.getSignature() + ", args: " + Arrays.toString(pjp.getArgs()) +
                ". Finish " + (System.currentTimeMillis() - startTime) + " ms");
        return retVal;
    }
}
