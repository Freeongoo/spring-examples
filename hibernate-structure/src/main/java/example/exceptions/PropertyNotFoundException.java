package example.exceptions;

public class PropertyNotFoundException extends BaseException {

    private static final long serialVersionUID = -6185340919121214905L;

    public PropertyNotFoundException(String message, ERROR_CODES code) {
        super(message, code);
    }
}
