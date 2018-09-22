package example.exceptions;

public class NotAllowAccessRuntimeException extends RuntimeException {
    public NotAllowAccessRuntimeException(String message) {
        super(message);
    }

    public NotAllowAccessRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
