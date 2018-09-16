package examples.controller;

import examples.exception.*;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ExceptionHandlingController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ExceptionHandlingController.class);

    // Unchecked exception
    @RequestMapping("/some-exception-unchecked")
    public void throwSomeRuntimeException() {
        throw new SomeRuntimeException("some runtime exception");
    }

    // local handle exception in this controller - do nothing
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

    // Unchecked exception
    // this OtherRuntimeException annotated by ResponseStatus
    @RequestMapping("/other-exception-unchecked-with-status")
    public void throwOtherRuntimeException() {
        throw new OtherRuntimeException("other exception");
    }

    // Unchecked exception
    @RequestMapping("/exception-custom-page")
    public void throwOtherOtherRuntimeException() {
        throw new OtherOtherRuntimeException("other other exception");
    }

    // Unchecked exception
    @RequestMapping("/exception-custom-page-response-entity")
    public void throwInfoRuntimeException() {
        throw new InfoRuntimeException("info exception");
    }
}
