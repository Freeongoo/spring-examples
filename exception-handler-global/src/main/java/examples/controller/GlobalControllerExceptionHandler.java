package examples.controller;

import examples.util.ExceptionUtil;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ExceptionHandlingController.class);


    // handle for all exception
    // except exception with annotation @ResponseStatus - handle by spring exception handler
    @ExceptionHandler(value = Exception.class)
    // we can set only some exceptions:
    // @ExceptionHandler(ThereIsNoSuchUserException.class)
    public ModelAndView
    defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;

        logger.error(e.getMessage());

        // Otherwise setup and send the user to a default error-view.
        ModelAndView mav = new ModelAndView();
        mav.addObject("ex", e);
        mav.addObject("ex_trace", ExceptionUtil.convertExceptionStackTraceToString(e));
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("custom-exception-page");
        return mav;
    }
}
