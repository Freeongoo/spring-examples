package examples.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "CheckedException throw")
public class OtherRuntimeException extends RuntimeException {

    public OtherRuntimeException(String message) {
        super(message);
    }

    public OtherRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
