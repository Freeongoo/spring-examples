# Example create multi modules in Spring Boot

in this example, it was specifically divided into many small modules to show how to connect them together.

## Modules:

* application - main module - run spring boot
* controller - rest controller
* model - entity
* repository - jpa repository

## Configuration Spring Boot with multi modules

#### Add to scan all modules

```
@ComponentScan({
        "application",
        "model",
        "repository",
        "controller"})
```

#### Add to scan Entity

```
@EntityScan("model")
```

#### Add to scan JPA repository

```
@EnableJpaRepositories("repository")
```
