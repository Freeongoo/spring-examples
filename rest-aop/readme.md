# Example jpa rest

## Configuration

For main and test dirs:
`cp application.properties.dist application.properties`

## Create aspect

Add annotations: `@Aspect` and `@Configuration`

Example: 
```
    @Aspect
    @Configuration
    public class SecurityAccessAspect {
    
        @Before("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
        public void check(JoinPoint joinPoint) throws NoSuchMethodException {
            RequestMapping req = getAnnotation(joinPoint, RequestMapping.class);
            String[] routeValues = req.value();
    
            for (String route: routeValues) {
                if (route.contains("/personal"))
                    throw new AccessDeniedException("Access denied", ErrorCode.ACCESS_DENIED);
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

