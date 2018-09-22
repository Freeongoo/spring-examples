# AOP

See tests for understand how aspects in spring work.

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