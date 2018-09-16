package examples.exception;

public class InfoRuntimeException extends RuntimeException {

    public InfoRuntimeException(String message) {
        super(message);
    }

    public InfoRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
