package hello.exception;

public class NotFoundException extends BaseException {

    private static final long serialVersionUID = 38816005992204401L;

    public NotFoundException(String message, ErrorCode code) {
        super(message, code);
    }
}
