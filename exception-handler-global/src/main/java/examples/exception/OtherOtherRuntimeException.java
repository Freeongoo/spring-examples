package examples.exception;

public class OtherOtherRuntimeException extends RuntimeException {

    public OtherOtherRuntimeException(String message) {
        super(message);
    }

    public OtherOtherRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
