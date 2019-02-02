package hello.exception;

public class NotValidParamsException extends BaseException {

    private static final long serialVersionUID = -1066219052235780119L;

    public NotValidParamsException(String message, ErrorCode code) {
        super(message, code);
    }
}