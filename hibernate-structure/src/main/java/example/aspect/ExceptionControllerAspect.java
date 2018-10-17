package example.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.exceptions.BaseException;
import example.exceptions.DuplicateFieldException;
import example.exceptions.PropertyNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ExceptionControllerAspect {

    @ExceptionHandler(DuplicateFieldException.class)
    @ResponseBody
    public void exception(DuplicateFieldException e, HttpServletResponse response) throws IOException {
        writeResponse(response, HttpServletResponse.SC_CONFLICT, e);
    }

    @ExceptionHandler(PropertyNotFoundException.class)
    @ResponseBody
    public void exception(PropertyNotFoundException e, HttpServletResponse response) throws IOException {
        writeResponse(response, HttpServletResponse.SC_BAD_REQUEST, e);
    }

    private void writeResponse(HttpServletResponse response, int code, BaseException e) throws IOException {
        printStackTrace (e);
        response.setStatus(code);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(e.getMapForResponse()));
        response.getWriter().flush();
    }

    private void printStackTrace (Exception e){
        // log.error(PREFIX, e);
    }
}
