# Internationalization Spring Boot

Run application and go to the browser:

* `http://localhost:8080` - demo for change lang
* `http://localhost:8080/local` - show current local code

# FAQ

## Where store my messages?

With Spring Boot, we can use the `messages.properties` (`/src/main/resources/messages.properties`) file which will be used for the default locale, US in our case.

For other language we must add local code, for example for French: `messages_fr.properties`

## How change default lang to other?

1. By application.properties to French:

```
spring.mvc.locale=fr
spring.mvc.locale-resolver=fixed
```

see `application.properties`

2. By bean configuration:

```
@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.US);
        return sessionLocaleResolver;
    }
}
```

see `AppConfig.java`

## How switch lang?

Use LocaleChangeInterceptor

```
@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
```

And then when we will pass the GET parameter: `?lang=fr` language will be changed to transmitted