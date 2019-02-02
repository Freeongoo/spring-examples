package hello.controller.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.BaseException;
import hello.exception.EmployeeNotFoundException;
import hello.exception.NotValidParamsException;
import hello.exception.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public void exception(ResourceNotFoundException e, HttpServletResponse response) throws IOException {
        writeResponse(response, HttpServletResponse.SC_NOT_FOUND, e);
    }

    @ExceptionHandler(NotValidParamsException.class)
    @ResponseBody
    public void exception(NotValidParamsException e, HttpServletResponse response) throws IOException {
        writeResponse(response, HttpServletResponse.SC_BAD_REQUEST, e);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseBody
    public void exception(EmployeeNotFoundException e, HttpServletResponse response) throws IOException {
        writeResponse(response, HttpServletResponse.SC_NOT_FOUND, e);
    }

    private void writeResponse(HttpServletResponse response, int code, BaseException e) throws IOException{
        response.setStatus(code);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(e.getMapForResponse()));
        response.getWriter().flush();
    }
}
