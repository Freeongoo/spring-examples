package hello.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.BaseException;
import hello.exception.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public void exception(NotFoundException e, HttpServletResponse response) throws IOException {
        writeResponse(response, HttpServletResponse.SC_NOT_FOUND, e);
    }

    private void writeResponse(HttpServletResponse response, int code, BaseException e) throws IOException{
        response.setStatus(code);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(e.getMapForResponse()));
        response.getWriter().flush();
    }
}
