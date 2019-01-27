# Welcome to learn routes in Spring!

1. Run application
2. Open in browser: `http://localhost:8080/` 
3. Read page :)

### Add controller and mapping to view from WebMvcConfigurer

```
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/simple-mvc-config").setViewName("simple-mvc-config");
    }
}
```

### Try inheritance abstract controller with base @RequestMapping

See example: 
- `/src/main/java/examples/controller/extend/AbstractApiController.java`
- `/src/main/java/examples/controller/extend/CompanyController.java`

the result of the company's route will be: `/companies`, not what we thought: `/api/companies`

See test: `/src/test/java/examples/controller/extend/AbstractApiControllerTest.java`