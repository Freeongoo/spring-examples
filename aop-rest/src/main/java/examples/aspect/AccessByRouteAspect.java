package examples.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;

@Aspect
@Configuration
public class AccessByRouteAspect {

    @Before("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void check(JoinPoint joinPoint) throws NoSuchMethodException {
        RequestMapping req = getAnnotation(joinPoint, RequestMapping.class);
        String[] routeValues = req.value();

        for (String route: routeValues) {
            if (route.equals("/secret"))
                throw new RuntimeException("Access denied");
        }
    }

    private <T extends Annotation> T getAnnotation(JoinPoint joinPoint, Class<T> clazz) throws NoSuchMethodException, SecurityException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
        return joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes).getAnnotation(clazz);
    }
}
