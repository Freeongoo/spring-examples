package hello.exception;

public class EmployeeNotFoundException extends BaseException {

    private static final long serialVersionUID = 8178368916339356132L;

    public EmployeeNotFoundException(String message, ErrorCode code) {
        super(message, code);
    }
}
