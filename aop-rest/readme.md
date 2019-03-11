# Example Spring REST and AOP

You need to clearly understand that Spring AOP only works for beans. 
If you need to check for classes that are not managed by Spring, 
you need to use AspectJ (for example: JPA entity)

## Create aspect

Add annotations: `@Aspect` and `@Configuration`

Example:
```
    @Aspect
    @Configuration
    public class AccessByRouteAspect {
    
        @Before("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
        public void check(JoinPoint joinPoint) throws NoSuchMethodException {
            RequestMapping req = getAnnotation(joinPoint, RequestMapping.class);
            String[] routeValues = req.value();
    
            for (String route: routeValues) {
                if (route.equals("/secret"))
                    throw new AccessDeniedException("Access denied");
            }
        }
    
        private <T extends Annotation> T getAnnotation(JoinPoint joinPoint, Class<T> clazz) throws NoSuchMethodException, SecurityException {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            String methodName = signature.getMethod().getName();
            Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
            return joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes).getAnnotation(clazz);
        }
    }
```

