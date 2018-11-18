# Interceptors in Spring Boot

Spring Interceptors has the ability to pre-handle and post-handle the web requests. Each interceptor class should extend the HandlerInterceptorAdapter class

## How connect interceptor

To add our interceptors into Spring configuration, we need to override addInterceptors() method inside WebConfig class that implements WebMvcConfigurer:

```
@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new OurSuperInterceptor());
    }
}
```

## Examples

#### 1. Use predefined interceptor for change language `LocaleChangeInterceptor`

See `/src/main/java/examples/AppConfig.java`

#### 2. Create custom interceptor - LoggerInterceptor

see `/src/main/java/examples/interceptor/LoggerInterceptor.java`