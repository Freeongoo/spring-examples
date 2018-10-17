package example.exceptions;

public class DuplicateFieldException extends BaseException {

    private static final long serialVersionUID = 1389344450671619619L;

    public DuplicateFieldException(String message, ERROR_CODES code) {
        super(message, code);
    }
}
