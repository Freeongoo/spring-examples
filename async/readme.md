# @Async

For add Async methods:
1. add in Application annotation: @EnableAsync
2. add for service method annotation: @Async

## The @Async Annotation
First – let’s go over the rules – @Async has two limitations:
* it must be applied to public methods only
* self-invocation – calling the async method from within the same class – won’t work

The reasons are simple – the method needs to be public so that it can be proxied. And self-invocation doesn’t work because it bypasses the proxy and calls the underlying method directly.

## Config thread executor

By default, Spring uses a SimpleAsyncTaskExecutor to actually run these methods asynchronously. The defaults can be overridden at two levels – at the application level or at the individual method level.

### Config by application level

See AsyncConfig.java

Set type of executor and config. And set exception handler for Exception Async

### Config by method level

See SimpleVoidService