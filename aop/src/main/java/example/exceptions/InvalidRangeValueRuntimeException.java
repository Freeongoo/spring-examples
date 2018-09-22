package example.exceptions;

public class InvalidRangeValueRuntimeException extends RuntimeException {
    public InvalidRangeValueRuntimeException(String message) {
        super(message);
    }

    public InvalidRangeValueRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
