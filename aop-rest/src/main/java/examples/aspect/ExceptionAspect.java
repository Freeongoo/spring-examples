package examples.aspect;

import examples.ExceptionUtil;
import examples.exceptions.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

@ControllerAdvice
public class ExceptionAspect {
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object processValidationError(AccessDeniedException ex) {
        String result = ExceptionUtil.convertExceptionStackTraceToString(ex);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("message", ex.getMessage());
        stringStringHashMap.put("stackTrace", result);

        return stringStringHashMap;
    }
}
