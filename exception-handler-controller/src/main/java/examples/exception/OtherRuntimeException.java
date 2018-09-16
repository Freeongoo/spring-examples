package examples.exception;

public class OtherRuntimeException extends RuntimeException {

    public OtherRuntimeException(String message) {
        super(message);
    }

    public OtherRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
