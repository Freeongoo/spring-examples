package example.aop.packagge;

import example.exceptions.NotAllowAccessRuntimeException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AccessDenyByPackageAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // for all classes methods in package example.aop.packagge.notallowaccess
    @Before("execution(* example.aop.packagge.notallowaccess.*.*(..))")
    public void before(JoinPoint joinPoint) {
        logger.info("before");
        throw new NotAllowAccessRuntimeException("denied");
    }
}
