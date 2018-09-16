package examples.controller;

import examples.exception.*;
import examples.util.ExceptionUtil;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ExceptionHandlingController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ExceptionHandlingController.class);

    // Unchecked exception
    @RequestMapping("/some-exception-unchecked")
    public void throwSomeRuntimeException() {
        throw new SomeRuntimeException("some runtime exception");
    }

    // handle exception SomeRuntimeException - only logging and ignore - nothing show
    @ExceptionHandler(SomeRuntimeException.class)
    public void handleSomeRuntimeException(Exception ex) {
        logger.error(ex.getMessage());
    }


    // Checked exception
    @RequestMapping("/some-exception-checked")
    public void throwCheckedException() throws CheckedException {
        throw new CheckedException("some exception checked");
    }

    // handle exception CheckedException - only logging and ignore - nothing show
    @ExceptionHandler(CheckedException.class)
    public void handleCheckedException(Exception ex) {
        logger.error(ex.getMessage());
    }

    // TODO: can pass in one @ExceptionHandler(value = {CheckedException.class, SomeRuntimeException.class})


    // Unchecked exception
    @RequestMapping("/other-exception-unchecked-with-status")
    public void throwOtherRuntimeException() {
        throw new OtherRuntimeException("other exception");
    }

    // handle exception SomeRuntimeException - logging
    // and set http status = 400 and message = "SomeRuntimeException handler executed"
    // and when we pass status - 400 - spring handle and show default exception page
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "SomeRuntimeException handler executed")
    @ExceptionHandler(OtherRuntimeException.class)
    public void handleOtherRuntimeException(Exception ex) {
        logger.error(ex.getMessage());
    }


    // Unchecked exception
    @RequestMapping("/exception-custom-page")
    public void throwOtherOtherRuntimeException() {
        throw new OtherOtherRuntimeException("other other exception");
    }

    // handle exception SomeRuntimeException - logging
    // and show custom exception page template
    @ExceptionHandler(OtherOtherRuntimeException.class)
    public ModelAndView handleOtherOtherRuntimeException(HttpServletRequest req, Exception ex) {
        logger.error("Request: " + req.getRequestURL() + " raised " + ex);

        ModelAndView modelAndView = new ModelAndView("custom-exception-page");
        modelAndView.addObject("url", req.getRequestURL());

        String stacktrace = ExceptionUtil.convertExceptionStackTraceToString(ex);

        modelAndView.addObject("ex", ex);
        modelAndView.addObject("ex_trace", stacktrace);

        return modelAndView;
    }


    // Unchecked exception
    @RequestMapping("/exception-custom-page-response-entity")
    public void throwInfoRuntimeException() {
        throw new InfoRuntimeException("info exception");
    }

    // handle exception SomeRuntimeException - logging
    // and show custom exception page template
    @ExceptionHandler(InfoRuntimeException.class)
    public ResponseEntity<?> handleInfoRuntimeException(HttpServletRequest req, Exception ex) {
        return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
    }
}
