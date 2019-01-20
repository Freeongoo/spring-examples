package hello.exception;

public enum ERROR_CODES {

    OBJECT_NOT_FOUND(1),
    ;

    private final int code;

    private ERROR_CODES (int code){
        this.code = code;
    }

    public int getValue(){
        return code;
    }
}
