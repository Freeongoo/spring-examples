# AOP

See tests for understand how aspects in spring work.

## Terms

* Pointcut: the expression used to define when a call to a method should be intercepted. In the above example, execution(* example.aop.packagge.notallowaccess.*.*(..)) is the pointcut.
* Advice: What do you want to do? An advice is the logic that you want to invoke when you intercept a method. In the above example, it is the code inside the before(JoinPoint joinPoint) method.
* Aspect: A combination of defining when you want to intercept a method call (Pointcut) and what to do (Advice) is called an Aspect.
* Join Point: When the code is executed and the condition for pointcut is met, the advice is executed. The Join Point is a specific execution instance of an advice.
* Weaver: Weaver is the framework that implements AOP — AspectJ or Spring AOP.

## Pointcut expression

* For all methods all classes for package `example.aop.packagge.notallowaccess`
```
"execution(* example.aop.packagge.notallowaccess.*.*(..))"
```

* For all methods class example.aop.clazz.FlowerDontTouch:
```
"execution(* example.aop.clazz.FlowerDontTouch.*())"
```

* For method getPassword() class example.aop.method.User:
```
"execution(* example.aop.method.User.getPassword())"
```

* For annotation "RangeValidate"
```
"@annotation(example.customannotation.RangeValidate)"
```