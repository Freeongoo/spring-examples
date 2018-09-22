package example.exceptions;

public class NotAllowAccessCheckedException extends Exception {
    public NotAllowAccessCheckedException(String message) {
        super(message);
    }

    public NotAllowAccessCheckedException(String message, Throwable cause) {
        super(message, cause);
    }
}
