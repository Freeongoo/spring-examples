package example.aop.annotation;

import example.customannotation.RangeValidate;
import example.exceptions.InvalidRangeValueRuntimeException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Configuration
public class RangeValidateAnnotationAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("@annotation(example.customannotation.RangeValidate)")
    public void before(JoinPoint joinPoint) {
        logger.info("before");
        Object [] args =  joinPoint.getArgs();

        RangeValidate rangeValidate = getRangeValidate(joinPoint);

        if (isNotExistArgs(args)) return;

        int testValue;

        Object argument = args [0];
        if (argument == null){
            return;
        } else if (argument.getClass().isPrimitive()){
            testValue = (int) argument;
        } else 	if (argument instanceof Number){
            testValue = (Integer) argument;
        } else {
            logger.warn("argument is not primitive or number");
            return;
        }

        logger.info(String.valueOf(testValue));

        if (!isRange(testValue, rangeValidate.from(), rangeValidate.to()))
            throw new InvalidRangeValueRuntimeException("invalid range");

    }

    private boolean isRange(int val, int from, int to) {
        return (val >= from && val <= to);
    }

    private RangeValidate getRangeValidate(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (!method.isAnnotationPresent(RangeValidate.class))
            logger.warn("Cannot found annotation RangeValidate");

        return method.getAnnotation(RangeValidate.class);
    }

    private boolean isNotExistArgs(Object[] args) {
        if (args == null || args.length == 0){
            logger.debug("no arguments found");
            return true;
        }
        return false;
    }

    /**
     * Getting an Annotation
     *
     * @param joinPoint
     * @param clazz
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    private <T extends Annotation> T getRequiredPermissions(JoinPoint joinPoint, Class<T> clazz) throws NoSuchMethodException, SecurityException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
        return joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes).getAnnotation(clazz);
    }
}
