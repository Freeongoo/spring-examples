package hello.exception;

public class AccessDeniedException extends BaseException {

    private static final long serialVersionUID = 8178368916339356132L;

    public AccessDeniedException(String message, ErrorCode code) {
        super(message, code);
    }
}
