package hello.exception;

public class ResourceNotFoundException extends BaseException {

    private static final long serialVersionUID = -7540315244100557301L;

    public ResourceNotFoundException(String message, ErrorCode code) {
        super(message, code);
    }
}
