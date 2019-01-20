package hello.exception;

public class NotFoundException extends BaseException {

    private static final long serialVersionUID = 38816005992204401L;

    public NotFoundException(String message, ERROR_CODES code) {
        super(message, code);
    }
}
