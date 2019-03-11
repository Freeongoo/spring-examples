package hello.exception;

public enum ErrorCode {

    OBJECT_NOT_FOUND(1),
    INVALID_PARAMS(2),
    ACCESS_DENIED(3),
    ;

    private final int code;

    private ErrorCode(int code){
        this.code = code;
    }

    public int getValue(){
        return code;
    }
}
