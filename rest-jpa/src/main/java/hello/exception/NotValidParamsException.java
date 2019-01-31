package hello.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotValidParamsException extends RuntimeException {
    public NotValidParamsException() {
        super();
    }

    public NotValidParamsException(String message) {
        super(message);
    }

    public NotValidParamsException(String message, Throwable cause) {
        super(message, cause);
    }
}